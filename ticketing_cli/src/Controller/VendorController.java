package Controller;

import java.util.Scanner;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.concurrent.locks.ReentrantLock;

import Runnable.VendorRunnable;

public class VendorController {

    private static final ReentrantLock lock = new ReentrantLock();
    private static final HttpClient client = HttpClient.newHttpClient();

    public void createVendorFromInput() {
        Scanner scanner = new Scanner(System.in);

        // Gather vendor details from user input
        System.out.print("Enter Vendor Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Vendor Contact (e.g., phone number): ");
        String contact = scanner.nextLine();

        System.out.print("Enter Vendor Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Vendor Password: ");
        String password = scanner.nextLine();

        // Create JSON string from user inputs
        String vendorJson = String.format(
                "{\"vendorName\": \"%s\", \"vendorContact\": \"%s\", \"vendorEmail\": \"%s\", \"vendorPassword\": \"%s\"}",
                name, contact, email, password);

        // Create a VendorRunnable instance with the 'save' task to save the vendor
        VendorRunnable vendorRunnable = new VendorRunnable("save", (Integer) null, vendorJson);

        // Run the VendorRunnable in a new thread
        Thread thread = new Thread(vendorRunnable);
        thread.start();

        System.out.println("Vendor creation request has been sent.");
    }

    public void getAllVendors() {
        VendorRunnable vendorRunnable = new VendorRunnable("fetchAll", (Integer) null, null);
        // Run the VendorRunnable in a new thread
        Thread thread = new Thread(vendorRunnable);
        thread.start();
    }

    public boolean vendorVerification(String username, String password) {
        try {
            // Build the URI with query parameters
            URI uri = new URI("http://localhost:8080/api/vendor/login?username=" + username + "&password=" + password);

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