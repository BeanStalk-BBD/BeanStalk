package src;

import java.io.IOException;
import java.util.Scanner;

public class CLIHandler {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        //setTerminalDimensions(16, 12);

        while (true) {
            StalkDraw.clear();
            System.out.println("                   🌱 b e a n s t a l k");
            System.out.println("                logged in a @chrisss");
            input = scanner.nextLine();
            // Process input here, or simply display it
            System.out.println("You entered: " + input);
            System.out.println("Press Enter to continue...");
            scanner.nextLine(); // Wait for user to press Enter to continue
        }
    }

       private static void setTerminalDimensions(int width, int height) {
        // Use ProcessBuilder to execute system commands
        try {
            new ProcessBuilder("cmd", "/c", "mode con cols=" + width + " lines=" + height)
                    .inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
