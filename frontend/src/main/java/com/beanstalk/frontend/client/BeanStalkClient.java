package com.beanstalk.frontend.client;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.beanstalk.frontend.model.Message;
import com.beanstalk.frontend.model.MessageResponse;
// import com.beanstalk.frontend.model.StalksResponse;

public interface BeanStalkClient {
    @GetExchange("/chats/{receiverName}/{senderID}/messages10")
    List<MessageResponse> getMessages(@PathVariable String receiverName, @PathVariable int senderID);

    @GetExchange("/chats/openchats/{userId}/{page}")
    List<String> getStalks(@PathVariable Integer userId, @PathVariable Integer page);

    @PostExchange("/messages/sendMessage/{receiverName}")
    Long postMessage(@PathVariable String receiverName, @RequestBody Message message);

}