package com.beanstalk.frontend.command;

import java.time.OffsetDateTime;
import java.util.List;

import com.beanstalk.frontend.client.BeanStalkClient;
import com.beanstalk.frontend.model.Message;
import com.beanstalk.frontend.model.MessageResponse;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class StalkCommand extends SecuredCommand {

    private final BeanStalkClient beanStalkClient;
    private final Integer userId;

    public StalkCommand(BeanStalkClient beanStalkClient) {
        this.beanStalkClient = beanStalkClient;
        this.userId = 1;
    }

    @ShellMethod("Displays a list of the most recent stalks.")
    @ShellMethodAvailability("isUserSignedIn")
    public List<String> stalks(@ShellOption(value = {"-P", "--page"}, defaultValue = "0") int page) {
        List<String> stalksResponse = beanStalkClient.getStalks(userId, page);
        return stalksResponse;
    }

    @ShellMethod("Sends a bean to the specified recipient and displays the relevant stalk.")
    @ShellMethodAvailability("isUserSignedIn")
    public void bean(@ShellOption({"-R", "--recipient"}) String recipient, @ShellOption({"-M", "--message"}) String message) {
        Message message_model = new Message(message, OffsetDateTime.now(), 1, userId);
        beanStalkClient.postMessage(recipient, message_model);
    }

    @ShellMethod("Displays the stalk for the specified recipient.")
    @ShellMethodAvailability("isUserSignedIn")
    public List<MessageResponse> stalk(@ShellOption({"-R", "--recipient"}) String recieverName) { //@ShellOption({"-R", "--recipient"}) String recipient
        List<MessageResponse> messagesResponse = beanStalkClient.getMessages(recieverName, userId);
        return messagesResponse;
    }

}
