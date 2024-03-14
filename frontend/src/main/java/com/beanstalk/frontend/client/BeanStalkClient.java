package com.beanstalk.frontend.client;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.beanstalk.frontend.model.AuthResponse;
import com.beanstalk.frontend.model.Login;
import com.beanstalk.frontend.model.Message;
import com.beanstalk.frontend.model.MessageResponse;
// import com.beanstalk.frontend.model.StalksResponse;

public interface BeanStalkClient {
    @PostExchange("https://github.com/login/device/code")
    String postLogin(@RequestBody Login login, @RequestHeader Map<String, String> headers);

    @GetExchange("/chats/{receiverName}/{senderID}/messages10")
    List<MessageResponse> getMessages(@PathVariable String receiverName, @PathVariable int senderID, @RequestHeader Map<String, String> headers);

    @GetExchange("/chats/openchats/{userId}/{page}")
    List<String> getStalks(@PathVariable Integer userId, @PathVariable Integer page, @RequestHeader Map<String, String> headers);

    @PostExchange("/messages/sendMessage/{receiverName}")
    Long postMessage(@PathVariable String receiverName, @RequestBody Message message, @RequestHeader Map<String, String> headers);
    
    @PostExchange("https://github.com/login/oauth/access_token")
    String postAuth(@RequestBody AuthResponse authResponse, @RequestHeader Map<String, String> headers);

    @GetExchange("/chats/getUserID")
    Integer getUserId(@RequestHeader Map<String, String> headers);
}
