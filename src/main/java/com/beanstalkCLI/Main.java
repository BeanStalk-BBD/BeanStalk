package com.beanstalkCLI;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.beanstalkCLI.Backend.StalkBackend;
import com.beanstalkCLI.Draw.StalkDraw;

@SpringBootApplication
public class Main {

    private static boolean hasBeenAuthenticated = true;
    private static Timer timer = new Timer();

    
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        Scanner scanner = new Scanner(System.in);
        String input;

        // setTerminalDimensions(16, 12);

        //StalkDraw.clear();
        System.out.println("                  b e a n s t a l k");
        System.out.println("                logged in as @chrisss");

        while (true) {
            input = scanner.nextLine();

            if (hasBeenAuthenticated) {
                if (input.charAt(0) == '@') {
                    switch (input.split(" ")[0]) {
                        case "@stalks":
                            Handle_Stalks();
                            break;
                        case "@help":
                            Handle_Help();
                            break;
                        case "@exit":
                            Handle_Exit();
                            break;
                        case "@version":
                            Handle_Version();
                            break;
                        default:
                            Handle_Name(input);
                            break;
                    }
                } else {
                    Handle_Help();
                }

            }
        }
    }

    private static void Handle_Help() {
        StalkDraw.clear();
        System.out.println("List of available commands:");
        System.out.println("\t@stalks \t\t Handle stalks");
        System.out.println("\t@help \t\t\t Display help information");
        System.out.println("\t@exit \t\t\t Exit the program");
        System.out.println("\t@<username> \t View a stalk with <username>");
        System.out.println("\t@<username> <message> \t Send a message <message> to user <username>");
    }

    private static void Handle_Exit() {
        System.exit(0);
    }

    private static void Handle_Version() {
        System.out.println("Version: 1.0.1");

    }

    private static void Handle_Stalks() {
        List<String> stalks;
        try {
            stalks = StalkBackend.GetStalks();
            StalkDraw.DrawStalk(stalks);

            recentUsername = ""; // Reset recent username
            recentlyRun = MethodType.STALKS;
        } catch (IOException | InterruptedException e) {
            Handle_Error(e);
        }
    }

    private static void Handle_Name(String input) {
        if (input.split(" ").length == 1) {
            Handle_GetStalk(input.substring(1));
        } else {
            Handle_SendMessage(input.split(" ")[0].substring(1), input.split(" ")[1]);
        }
    }

    private static void Handle_SendMessage(String username, String message) {
        try {
            StalkBackend.SendMessage(username, message);
        } catch (IOException | InterruptedException e) {
            Handle_Error(e);
        }
    }

    private static void Handle_GetStalk(String username) {
        try {
            StalkBackend.GetSingleStalk(username);
            recentUsername = username;
            recentlyRun = MethodType.NAME;
        } catch (IOException | InterruptedException e) {
            Handle_Error(e);
        }
    }

    private static void Handle_Error(Exception e) {
        e.printStackTrace();
    }

    private static void startTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (recentlyRun == MethodType.STALKS) {
                    Handle_Stalks();
                } else {
                    Handle_Name(recentUsername);
                }
            }
        }, 0, 2500);
    }

    private static enum MethodType {
        STALKS, NAME
    }

    private static MethodType recentlyRun = MethodType.STALKS;
    private static String recentUsername = "";

}

@Controller
class WebController {

    @GetMapping("/")
    public String home() {
        return "index";
    }
}
