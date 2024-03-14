package com.beanstalk.frontend.command;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;
import com.beanstalk.frontend.client.BeanStalkClient;
import com.beanstalk.frontend.model.AuthResponse;
import com.beanstalk.frontend.model.Login;
import com.beanstalk.frontend.model.Message;
import com.beanstalk.frontend.model.MessageResponse;
import com.beanstalk.frontend.shell.ShellHelper;
import com.beanstalk.frontend.utils.Stalk;

import org.jline.reader.LineReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class StalkCommand extends SecuredCommand {

    private final BeanStalkClient beanStalkClient;
    private final Integer userId;
    private final String client_id = "d3627dbaee1703774ddd";
    private Map<String, String> headerMap = new HashMap<>();

    @Autowired
    ShellHelper shellHelper;

    public StalkCommand(BeanStalkClient beanStalkClient) {
        this.beanStalkClient = beanStalkClient;
        this.userId = 1;
        this.headerMap.put("Content-Type", "application/json");
    }

    @ShellMethod("Log in with GitHub.")
    public void login() {
        Map<String, String> responseMap = convertStringToMap(beanStalkClient.postLogin(new Login(client_id), headerMap));
        shellHelper.print(String.format(
            "Please follow the link: https://github.com/login/device \nYour code is: %s \n%s", 
            responseMap.get("user_code"), 
            responseMap.get("device_code")
        ));
        // AuthResponse authResponse = new AuthResponse(client_id, "", "");
        Map<String, String> authMap = new HashMap<String, String>();
        do {
            authMap = getAuthMap(responseMap.get("device_code"));
            if (authMap.containsKey("access_token")) {
                shellHelper.printSuccess("Login successful!");
                break;
            }
            else {
                if (authMap.get("error").equals("authorization_pending")) {
                    shellHelper.printError("Awaiting authorization: please entered the provided code at https://github.com/login/device.");
                }
                else if (authMap.get("error").equals("expired_token")) {
                    shellHelper.printError("Authorization code expired: please run the 'login' command again.");
                    break;
                }
                else {
                    shellHelper.printError("Unspecified error: please run the 'login' command again.");
                    break;
                }
            }
        } while (true);
    }

    @ShellMethod("Displays a list of the most recent stalks.")
    @ShellMethodAvailability("isUserSignedIn")
    public List<String> stalks(@ShellOption(value = {"-P", "--page"}, defaultValue = "0") int page) {
        List<String> stalksResponse = beanStalkClient.getStalks(userId, page);
        return stalksResponse;
    }

    @ShellMethod("Sends a bean to the specified recipient and displays the relevant stalk.")
    @ShellMethodAvailability("isUserSignedIn")
    public String bean(@ShellOption({"-R", "--recipient"}) String recipient, @ShellOption({"-M", "--message"}) String message) {
        Message message_model = new Message(message, OffsetDateTime.now(), 0, userId);
        beanStalkClient.postMessage(recipient, message_model);
        List<MessageResponse> messagesResponse = beanStalkClient.getMessages(recipient, userId);
        return Stalk.CreateStalk(messagesResponse, recipient, userId); 
    }

    @ShellMethod("Displays the stalk for the specified recipient.")
    @ShellMethodAvailability("isUserSignedIn")
    public String stalk(@ShellOption({"-R", "--recipient"}) String recipient) { 
        List<MessageResponse> messagesResponse = beanStalkClient.getMessages(recipient, userId);
        return Stalk.CreateStalk(messagesResponse, recipient, userId);
    }

    private Map<String, String> convertStringToMap(String data) {
        Map<String, String> map = new HashMap<>();
        StringTokenizer tokenizer = new StringTokenizer(data, "&");
    
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            String[] keyValue = token.split("=");
            map.put(keyValue[0], keyValue[1]);
        }
    
        return map;
    }

    private Map<String, String> getAuthMap(String device_code) {
        shellHelper.prompt("\n\nPress 'enter' to continue once you have authorised this application...");
        AuthResponse authResponse = new AuthResponse(client_id, device_code, "urn:ietf:params:oauth:grant-type:device_code");
        String authStr = beanStalkClient.postAuth(authResponse, headerMap).split("&")[0];
        return convertStringToMap(authStr);
    }

}
