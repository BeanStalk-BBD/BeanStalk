package com.beanstalk.frontend.command;

import org.springframework.shell.Availability;

public abstract class SecuredCommand {
    private boolean someFlag = true;
    public Availability isUserSignedIn() {
        if (someFlag) {
            return Availability.available();
        }
        else {
            return Availability.unavailable("you are not signed in. Please sign in to be able to use this command!");
        }
        
    }
}
