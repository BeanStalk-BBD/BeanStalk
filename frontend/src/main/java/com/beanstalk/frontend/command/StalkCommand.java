package com.beanstalk.frontend.command;

import java.time.OffsetDateTime;
import java.util.List;

import com.beanstalk.frontend.client.BeanStalkClient;
import com.beanstalk.frontend.model.Message;
import com.beanstalk.frontend.model.MessageResponse;
import com.beanstalk.frontend.utils.Stalk;

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

    // @ShellMethod("TEST")
    // public String test(@ShellOption({"-R", "--recipient"}) String recipient) { 
    //     // List<MessageResponse> messagesResponse = beanStalkClient.getMessages(recipient, userId);
    //     List<MessageResponse> messagesResponse =  new ArrayList<MessageResponse>();
    //     messagesResponse.add(new MessageResponse(4L, "wud", OffsetDateTime.now(), 1, 1));
    //     messagesResponse.add(new MessageResponse(3L, "hi", OffsetDateTime.now(), 1, 2));
    //     messagesResponse.add(new MessageResponse(2L, "oh", OffsetDateTime.now(), 1, 2));
    //     messagesResponse.add(new MessageResponse(1L, "hi", OffsetDateTime.now(), 1, 1));
    //     return Stalk.CreateStalk(messagesResponse, recipient, userId);
    // }

}
