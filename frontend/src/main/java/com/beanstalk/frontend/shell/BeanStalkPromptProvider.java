package com.beanstalk.frontend.shell;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class BeanStalkPromptProvider implements PromptProvider {

    @Override
    public AttributedString getPrompt() {
        return new AttributedString("Bean-Stalk:>", 
            AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN)
        );
    }
}
