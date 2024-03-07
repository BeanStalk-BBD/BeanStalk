package com.beanstalk.beanstalk;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class getMessageController {
    

    @GetMapping("/Messages/getMessage")
    public String index(){
        return "hello World!";
    }
}
