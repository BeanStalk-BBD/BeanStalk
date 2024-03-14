package com.beanstalk.frontend.shell;

import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Value;

public class ShellHelper {
    @Value("${shell.out.info}")
    public String infoColor;

    @Value("${shell.out.success}")
    public String successColor;

    @Value("${shell.out.warning}")
    public String warningColor;

    @Value("${shell.out.error}")
    public String errorColor;

    private Terminal terminal;
    private LineReader lineReader;

    public ShellHelper(Terminal terminal, LineReader lineReader) {
        this.terminal = terminal;
        this.lineReader = lineReader;
    }

    public String getColored(String message, PromptColour color) {
        return (new AttributedStringBuilder()).append(message, AttributedStyle.DEFAULT.foreground(color.toJlineAttributedStyle())).toAnsi();
    }

    public String getInfoMessage(String message) {
        return getColored(message, PromptColour.valueOf(infoColor));
    }

    public String getSuccessMessage(String message) {
        return getColored(message, PromptColour.valueOf(successColor));
    }

    public String getWarningMessage(String message) {
        return getColored(message, PromptColour.valueOf(warningColor));
    }

    public String getErrorMessage(String message) {
        return getColored(message, PromptColour.valueOf(errorColor));
    }


    public void print(String message) {
        print(message, null);
    }

    public void printSuccess(String message) {
        print(message, PromptColour.valueOf(successColor));
    }

 
    public void printInfo(String message) {
        print(message, PromptColour.valueOf(infoColor));
    }


    public void printWarning(String message) {
        print(message, PromptColour.valueOf(warningColor));
    }


    public void printError(String message) {
        print(message, PromptColour.valueOf(errorColor));
    }


    public void print(String message, PromptColour colour) {
        String toPrint = message;
        if (colour != null) {
            toPrint = getColored(message, colour);
        }
        terminal.writer().println(toPrint);
        terminal.flush();
    }

    public String prompt(String  prompt) {
        String answer = lineReader.readLine(prompt);
        return answer;
    }
}
