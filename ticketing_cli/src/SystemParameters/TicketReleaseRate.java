package SystemParameters;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TicketReleaseRate {
    private final HttpClient httpClient;
    private final String endpoint = "http://localhost:8080/api/config/ticket-release-rate";

    public TicketReleaseRate(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String getValue() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
