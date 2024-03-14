package com.beanstalk.frontend.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import com.beanstalk.frontend.shell.ShellHelper;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

@ShellComponent
public class BeanyCommand {
    @Autowired
    ShellHelper shellHelper;

    @ShellMethod("For when it's 2am.")
    public String feelingbeany() throws IOException {
        try {         
            return shellHelper.getSuccessMessage(Files.readString(Paths.get("src\\main\\resources\\beany_boys.txt")));
        }
        catch (Exception e) {
            return "No beans for you :(";
        }
        
    }
}
