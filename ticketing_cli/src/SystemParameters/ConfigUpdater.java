package SystemParameters;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConfigUpdater {
    private final HttpClient httpClient;

    public ConfigUpdater(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void updateProperty(String key, String value) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/config"))
                .PUT(HttpRequest.BodyPublishers.ofString("key=" + key + "&value=" + value))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();

        HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());

        if (response.statusCode() == 200) {
            System.out.println("Property updated successfully.");
        } else {
            System.out.println("Failed to update property: " + response.statusCode());
        }
    }
}
