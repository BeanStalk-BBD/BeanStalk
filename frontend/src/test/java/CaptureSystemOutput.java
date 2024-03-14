package com.beanstalk.frontend.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CaptureSystemOutput {
    private static PrintStream originalOut = System.out;
    private static ByteArrayOutputStream outputStream;

    public static OutputCapture startCapture() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        return new OutputCapture(outputStream);
    }

    public static class OutputCapture {
        private final ByteArrayOutputStream outputStream;

        private OutputCapture(ByteArrayOutputStream outputStream) {
            this.outputStream = outputStream;
        }

        public String stopCapture() {
            System.setOut(originalOut);
            return outputStream.toString();
        }
    }
}
