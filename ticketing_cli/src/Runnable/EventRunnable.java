package Runnable;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.locks.ReentrantLock;

public class EventRunnable implements Runnable {
    private final String task;
    private final Integer eventId;
    private final Integer vendorId;
    private final String eventJson; // JSON data for creating an event
    private static final ReentrantLock lock = new ReentrantLock();
    private static final HttpClient client = HttpClient.newHttpClient();

    public EventRunnable(String task, Integer eventId, Integer vendorId, String eventJson) {
        this.task = task;
        this.eventId = eventId;
        this.vendorId = vendorId;
        this.eventJson = eventJson;
    }

    @Override
    public void run() {
        switch (task) {
            case "fetchAll":
                fetchAllEvents();
                break;
            case "fetchById":
                fetchEventById(eventId);
                break;
            case "fetchByVendorId":
                fetchEventsByVendorId(vendorId);
                break;
            case "save":
                saveEvent(eventJson);
                break;
            case "delete":
                deleteEvent(eventId);
                break;
            default:
                System.out.println("Invalid event task specified.");
        }
    }

    private void fetchAllEvents() {
        lock.lock();
        try {
            // Send GET request to the event API
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/event"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Get the response body and remove surrounding brackets if it's a JSON array
            String jsonResponse = response.body().trim();
            if (jsonResponse.startsWith("[") && jsonResponse.endsWith("]")) {
                jsonResponse = jsonResponse.substring(1, jsonResponse.length() - 1);
            }

            // Split the JSON array by each object entry
            String[] events = jsonResponse.split("},\\s*\\{");
            System.out.println("\n");

            // Loop through each event's data and print details
            for (String event : events) {
                // Clean up each event JSON entry by adding braces back
                event = "{" + event.replaceAll("^[{\\[]|[}\\]]$", "") + "}";
                System.out.println("Event Details =>");

                // Split key-value pairs and print each field
                String[] fields = event.split(",\\s*\"");
                for (String field : fields) {
                    String[] keyValue = field.split(":\\s*");
                    if (keyValue.length == 2) {
                        // Remove quotes from keys and values
                        String key = keyValue[0].replaceAll("[\"{}]", "").trim();
                        String value = keyValue[1].replaceAll("[\"{}]", "").trim();
                        System.out.println(key + ": " + value);
                    }
                }
                System.out.println();  // Separate each event by a blank line
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    private void fetchEventById(int eventId) {
        lock.lock();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/event/" + eventId))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Event Data: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void fetchEventsByVendorId(int vendorId) {
        lock.lock();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/event/vendor/" + vendorId))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Events for Vendor ID " + vendorId + ": " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void saveEvent(String eventJson) {
        lock.lock();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/event"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(eventJson))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Saved Event: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void deleteEvent(int eventId) {
        lock.lock();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/event/" + eventId))
                    .DELETE()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Deleted Event: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
