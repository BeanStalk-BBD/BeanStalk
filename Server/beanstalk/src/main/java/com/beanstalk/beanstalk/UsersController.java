package com.beanstalk.beanstalk;
import com.beanstalk.beanstalk.Users;
import com.beanstalk.beanstalk.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
//Users
@RequestMapping("/")
public class UsersController {
    @Autowired
    UsersService userService;

    @GetMapping(value="/", produces="application/json")
    public List<Users> getUsers(){
        return userService.list();
    }

}
