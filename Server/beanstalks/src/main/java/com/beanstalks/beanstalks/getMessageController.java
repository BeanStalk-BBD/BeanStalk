package com.beanstalks.beanstalks;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Messages/getMessages")
// @EnableAutoConfiguration
public class getMessageController {
    @GetMapping
    public String getMessages(){
        return("hello world");
    }
}
