package com.beanstalk.frontend.utils;

import com.beanstalk.frontend.model.MessageResponse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StalkTest {

    @Test
    void testCreateStalk() {
        List<MessageResponse> messages = new ArrayList<>();
        messages.add(new MessageResponse(1L, "Hello", OffsetDateTime.now(), 1, 2));
        messages.add(new MessageResponse(2L, "How are you?", OffsetDateTime.now(), 2, 1));
        messages.add(new MessageResponse(3L, "Fine, thanks", OffsetDateTime.now(), 1, 2));
        
        String recipient = "Alice";
        Integer userId = 1;

        // Redirect System.out to capture printed output
        CaptureSystemOutput.OutputCapture capture = CaptureSystemOutput.startCapture();

        // Call the method under test
        String printedOutput = Stalk.CreateStalk(messages, recipient, userId);

        // Check if something was printed
        assertFalse(printedOutput.isEmpty());
    }

    @Test
    void testDrawMessage() {
        String message = "Hello";
        boolean isFirst = true;
        boolean isSender = true;

        String expectedOutput = "                                ______________________\n" +
                                "                            ___/ Hello                \\\n" +
                                "                           /   \\______________________/\n";
    


        String actualOutput = Stalk.DrawMessage(message, isFirst, isSender);

        assertEquals(expectedOutput, actualOutput);
    }
}
