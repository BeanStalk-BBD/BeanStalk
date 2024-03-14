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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class StalkCommand {

    private final BeanStalkClient beanStalkClient;
    private Integer userId;
    private final String client_id = "d3627dbaee1703774ddd";
    private Map<String, String> loginHeaderMap = new HashMap<>();
    private Map<String, String> headerMap = new HashMap<>();

    @Autowired
    ShellHelper shellHelper;

    public StalkCommand(BeanStalkClient beanStalkClient) {
        this.beanStalkClient = beanStalkClient;
        this.loginHeaderMap.put("Content-Type", "application/json");
    }

    @ShellMethod("Log in with GitHub.")
    public void login() {
        Map<String, String> responseMap = convertStringToMap(beanStalkClient.postLogin(new Login(client_id), loginHeaderMap));
        shellHelper.print(String.format(
            "Please follow the link: https://github.com/login/device \nYour code is: %s", 
            responseMap.get("user_code")
        ));
        
        Map<String, String> authMap = new HashMap<String, String>();
        do {
            authMap = getAuthMap(responseMap.get("device_code"));
            if (authMap.containsKey("access_token")) {
                this.headerMap.put("Authorization", "Bearer " + authMap.get("access_token"));
                shellHelper.printSuccess("Login successful!");
                this.userId = beanStalkClient.getUserId(headerMap);
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
        List<String> stalksResponse = beanStalkClient.getStalks(userId, page, headerMap);
        return stalksResponse;
    }

    @ShellMethod("Sends a bean to the specified recipient and displays the relevant stalk.")
    @ShellMethodAvailability("isUserSignedIn")
    public String bean(@ShellOption({"-R", "--recipient"}) String recipient, @ShellOption({"-M", "--message"}) String message) {
        try {
            if (message.length() > 20)
            return shellHelper.getErrorMessage("A message may not be longer than 20 characters!");
            Message message_model = new Message(message, OffsetDateTime.now(), 0, userId);
            beanStalkClient.postMessage(recipient, message_model, headerMap);
            List<MessageResponse> messagesResponse = beanStalkClient.getMessages(recipient, userId, headerMap);
            return Stalk.CreateStalk(messagesResponse, recipient, userId); 
        }
        catch (Exception e) {
            return getErrorMessage(e.getMessage());
        }
    }

    @ShellMethod("Displays the stalk for the specified recipient.")
    @ShellMethodAvailability("isUserSignedIn")
    public String stalk(@ShellOption({"-R", "--recipient"}) String recipient) { 
        try {
            List<MessageResponse> messagesResponse = beanStalkClient.getMessages(recipient, userId, headerMap);
            return Stalk.CreateStalk(messagesResponse, recipient, userId);
        }
        catch (Exception e) {
            return getErrorMessage(e.getMessage());
        }
        
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
        String authStr = beanStalkClient.postAuth(authResponse, loginHeaderMap).split("&")[0];
        return convertStringToMap(authStr);
    }

    private String getErrorMessage(String e) {
        return shellHelper.getErrorMessage(
                e.split(" ")[0].equals("404") 
                    ? "The specified user doesn't exist!" 
                    : "Unspecified error: please try again!"
        );
    }

    public Availability isUserSignedIn() {
        return (userId == null 
            ? Availability.unavailable("you are not logged in. Please log in to be able to use this command!") 
            : Availability.available()
        );        
    }

}
