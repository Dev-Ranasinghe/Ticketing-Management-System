package Controller;

import Service.CustomerService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CustomerController {

    private final HttpClient client = HttpClient.newHttpClient();
    private String username;

    // Set the username during login
    public void setUsername(String username) {
        this.username = username;
    }

    // Get the stored username
    public String getUsername() {
        return this.username;
    }

    public void getAllCustomers() {
        // Create a new thread for each service task and start it
        CustomerService service = new CustomerService("fetchAll", null, null);
        Thread thread = new Thread(service);
        thread.start();
        try {
            thread.join();  // Waits for this thread to complete before moving on
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getCustomerById(int customerId) {
        CustomerService service = new CustomerService("fetchById", customerId, null);
        Thread thread = new Thread(service);
        thread.start();
        try {
            thread.join();  // Waits for this thread to complete before moving on
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void saveCustomer(String customerJson) {
        CustomerService service = new CustomerService("save", null, customerJson);
        Thread thread = new Thread(service);
        thread.start();
        try {
            thread.join();  // Waits for this thread to complete before moving on
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(int customerId) {
        CustomerService service = new CustomerService("delete", customerId, null);
        Thread thread = new Thread(service);
        thread.start();
        try {
            thread.join();  // Waits for this thread to complete before moving on
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean customerVerification(String username, String password) {
        try {
            // Build the URI with query parameters
            URI uri = new URI("http://localhost:8080/api/customer/login?username=" + username + "&password=" + password);

            // Create the GET request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse and return the boolean response
            return Boolean.parseBoolean(response.body());

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
