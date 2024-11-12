package Runnable;

import java.net.URI;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.concurrent.locks.ReentrantLock;

public class VendorRunnable implements Runnable {
    private final String task;
    private final Integer vendorId;
    private final String vendorJson; // JSON data for creating or updating a vendor
    private static final ReentrantLock lock = new ReentrantLock();
    private static final HttpClient client = HttpClient.newHttpClient();

    public VendorRunnable(String task, Integer vendorId, String vendorJson) {
        this.task = task;
        this.vendorId = vendorId;
        this.vendorJson = vendorJson;
    }

    @Override
    public void run() {
        switch (task) {
            case "fetchAll":
                fetchAllVendors();
                break;
            case "fetchById":
                fetchVendorById(vendorId);
                break;
            case "save":
                saveVendor(vendorJson);
                break;
            case "delete":
                deleteVendor(vendorId);
                break;
            default:
                System.out.println("Invalid vendor task specified.");
        }
    }

    public void fetchAllVendors() {
        lock.lock();
        try {
            // Send GET request to the vendor API
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/vendor"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Get the response body and remove the surrounding brackets
            String jsonResponse = response.body().trim();

            if (jsonResponse.startsWith("[") && jsonResponse.endsWith("]")) {
                jsonResponse = jsonResponse.substring(1, jsonResponse.length() - 1);
            }

            // Split the JSON array by each object entry
            String[] vendors = jsonResponse.split("},\\s*\\{");
            System.out.println("\n");
            // Loop through each vendor's data and print details
            for (String vendor : vendors) {
                // Clean up each vendor JSON entry by adding braces back
                vendor = "{" + vendor.replaceAll("^[{\\[]|[}\\]]$", "") + "}";
                System.out.println("Vendor Details =>");

                // Split key-value pairs and print each field
                String[] fields = vendor.split(",\\s*\"");
                for (String field : fields) {
                    String[] keyValue = field.split(":\\s*");
                    if (keyValue.length == 2) {
                        // Remove quotes from keys and values
                        String key = keyValue[0].replaceAll("[\"{}]", "").trim();
                        String value = keyValue[1].replaceAll("[\"{}]", "").trim();
                        System.out.println(key + ": " + value);
                    }
                }
                System.out.println();  // Separate each vendor by a blank line
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void fetchVendorById(int vendorId) {
        lock.lock();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/vendor/" + vendorId))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Vendor Data: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void saveVendor(String vendorJson) {
        lock.lock();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/vendor"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(vendorJson))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println("Saved Vendor: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void deleteVendor(int vendorId) {
        lock.lock();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/vendor/" + vendorId))
                    .DELETE()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Deleted Vendor: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
