package Runnable;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.locks.ReentrantLock;

public class CustomerRunnable implements Runnable {
    private final String task;
    private final Integer customerId;
    private final String customerJson; // JSON data for creating or updating a customer
    private static final ReentrantLock lock = new ReentrantLock();
    private static final HttpClient client = HttpClient.newHttpClient();

    public CustomerRunnable(String task, Integer customerId, String customerJson) {
        this.task = task;
        this.customerId = customerId;
        this.customerJson = customerJson;
    }

    @Override
    public void run() {
        switch (task) {
            case "fetchAll":
                fetchAllCustomers();
                break;
            case "fetchById":
                fetchCustomerById(customerId);
                break;
            case "save":
                saveCustomer(customerJson);
                break;
            case "delete":
                deleteCustomer(customerId);
                break;
            default:
                System.out.println("Invalid customer task specified.");
        }
    }

    private void fetchAllCustomers() {
        lock.lock();
        try {
            // Send GET request to the customer API
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/customer"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Get the response body and remove surrounding brackets if it's a JSON array
            String jsonResponse = response.body().trim();
            if (jsonResponse.startsWith("[") && jsonResponse.endsWith("]")) {
                jsonResponse = jsonResponse.substring(1, jsonResponse.length() - 1);
            }

            // Split the JSON array by each object entry
            String[] customers = jsonResponse.split("},\\s*\\{");
            System.out.println("\n");

            // Loop through each customer's data and print details
            for (String customer : customers) {
                // Clean up each customer JSON entry by adding braces back
                customer = "{" + customer.replaceAll("^[{\\[]|[}\\]]$", "") + "}";
                System.out.println("Customer Details =>");

                // Split key-value pairs and print each field
                String[] fields = customer.split(",\\s*\"");
                for (String field : fields) {
                    String[] keyValue = field.split(":\\s*");
                    if (keyValue.length == 2) {
                        // Remove quotes from keys and values
                        String key = keyValue[0].replaceAll("[\"{}]", "").trim();
                        String value = keyValue[1].replaceAll("[\"{}]", "").trim();
                        System.out.println(key + ": " + value);
                    }
                }
                System.out.println();  // Separate each customer by a blank line
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    private void fetchCustomerById(int customerId) {
        lock.lock();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/customer/" + customerId))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Customer Data: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void saveCustomer(String customerJson) {
        lock.lock();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/customer"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(customerJson))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Saved Customer: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void deleteCustomer(int customerId) {
        lock.lock();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/customer/" + customerId))
                    .DELETE()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Deleted Customer: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
