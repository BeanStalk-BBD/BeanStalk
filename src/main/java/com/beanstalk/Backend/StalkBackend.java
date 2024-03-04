package src.main.java.com.beanstalk.Backend;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class StalkBackend {

    private static final String BASE_URL = "http://example.com/api"; // Change this to your API base URL

    public static List<String> GetMessages(String username) throws IOException, InterruptedException {
        String url = BASE_URL + "/messages?username=" + username;
        return makePostRequest(url);
    }

    public static List<String> GetStalks() throws IOException, InterruptedException {
        String url = BASE_URL + "/stalks";
        return makePostRequest(url);
    }

    public static List<String> SendMessage(String username, String message) throws IOException, InterruptedException {
        String url = BASE_URL + "/send";
        // Prepare POST request body
        String requestBody = "username=" + username + "&message=" + message;
        return makePostRequest(url, requestBody);
    }

    public static List<String> GetSingleStalk(String username) throws IOException, InterruptedException {
        String url = BASE_URL + "/stalk?username=" + username;
        return makePostRequest(url);
    }

    private static List<String> makePostRequest(String url) throws IOException, InterruptedException {
        // Create HttpClient
        HttpClient client = HttpClient.newHttpClient();
        // Create HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        // Send request and get response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // Process response
        if (response.statusCode() == 200) {
            return parseResponse(response.body());
        } else {
            throw new IOException("Failed to retrieve data: " + response.statusCode());
        }
    }

    private static List<String> makePostRequest(String url, String body) throws IOException, InterruptedException {
        // Create HttpClient
        HttpClient client = HttpClient.newHttpClient();
        // Create HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        // Send request and get response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        // Process response
        if (response.statusCode() == 200) {
            return parseResponse(response.body());
        } else {
            throw new IOException("Failed to retrieve data: " + response.statusCode());
        }
    }

    private static List<String> parseResponse(String responseBody) {
        // Implement parsing logic here based on the format of the response
        // For example, split the response into individual messages/stalks
        // and return them as a List<String>
        List<String> result = new ArrayList<>();
        // Parse the response body and populate the result list
        return result;
    }
}
