package com.beanstalk.frontend.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.beanstalk.frontend.model.MessageResponse;

public class Stalk {
    public static String CreateStalk(List<MessageResponse> messages, String recipient, Integer userId) {
        StringBuilder stalk = new StringBuilder();
        if (!messages.isEmpty()) {
            MessageResponse firstMessage = messages.stream().reduce((first, second) -> second).orElse(null);
            stalk.append(DrawMessage(firstMessage.messageContent(), true, userId==firstMessage.messageSender()));
            messages.stream().limit(messages.size()-1).collect(Collectors.toCollection(LinkedList::new))
                .descendingIterator()
                .forEachRemaining(x -> stalk.append(DrawMessage(x.messageContent(), false, userId==x.messageSender())));
        }

        stalk.append("                           |\n");
        stalk.append(String.format("  @%-24s|\n", recipient));
        stalk.append("__________________________/|\\__________________________\n");

        return stalk.toString();
    }
    
    public static String DrawMessage(String message, boolean isFirst, boolean isSender) {
        String spaces = " ".repeat(27);
        String stem = (isFirst ? " " : "|");

        return String.format(
            "%s______________________%s\n%s/ %-20s \\%s\n%s\\______________________/%s\n", 
            (isSender ? spaces + stem + "    " : " "), 
            (isSender ? "" : "    " + stem), 
            (isSender ? spaces + stem + "___" : ""), 
            message,
            (isSender ? "" : "___" + stem), 
            (isSender ? spaces + (isFirst ? "/" : "|") + "   " : ""), 
            (isSender ? "" : "   " + (isFirst ? "\\" : "|"))
        );
    }
}
