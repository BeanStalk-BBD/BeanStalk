package src;
import java.util.List;

public class StalkDraw {

    public static void DrawStalk(List<String> messages) {

        int longestMessage = 20;

        DrawMessage(messages.get(0), longestMessage, true, true);

        for (String message : messages.subList(1, messages.size())) {
            DrawMessage(message, longestMessage, false, messages.indexOf(message) % 2 == 0);
        }

        DrawFoot(longestMessage, "@Lior123");
    }

    public static void DrawMessage(String message, int longestMessage, boolean isFirst, boolean isSender) {
        String prefix = "";
        String dashes = "_".repeat(longestMessage);
        String padding = " ".repeat(longestMessage - message.length() - 4);


        if (isSender)
        {
            prefix = " ".repeat(longestMessage + 5);
            prefix = prefix + (isFirst ? " " : "|");

            System.out.println(prefix + "    " + dashes);
            System.out.println(prefix + "___/  " + message + padding + "  \\");
            System.out.println(prefix.substring(0, prefix.length() - 1) + (isFirst ? "/" : "|") + "   \\" + dashes   + "/");
        }
        else if (!isSender)
        {
        String addStalkTop = "    |";

        System.out.println(prefix + " " + dashes + (isFirst ? "" : addStalkTop));
        System.out.println(prefix + "/  " + message + padding + "  \\___" + (isFirst ? "" : "|"));
        System.out.println(prefix + "\\" + dashes   + "/   |");
        }
    }

    public static void DrawFoot(int longestMessage, String recipientName)
    {
        String ground = "_".repeat(longestMessage + 4);
        String prefix = " ".repeat(longestMessage + 5);
        prefix = prefix + "|";

        for (int i = 0; i < 2; i++){
            if (i == 1) 
            {
                prefix = "  " + recipientName + prefix.subSequence(2 + recipientName.length(), prefix.length());
            }
            System.out.println(prefix);
        }

        System.out.println(ground + "/|\\" + ground);
    }

    // Method to clear the console
    public static void clear() {
    try {
        final String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            // For Windows
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            // For Linux/Unix/MacOS
            Runtime.getRuntime().exec("clear");
        }
    } catch (Exception e) {
        // Handle exceptions
        System.out.println("Error while clearing console: " + e.getMessage());
    }
}

    public static void main(String[] args) {
        // Example usage:
        DrawStalk(List.of("hi", "hello", "how are you?",
                "i am good"));
    }
}
