package Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.locks.ReentrantLock;

public class VendorService implements Runnable {

    private static final ReentrantLock lock = new ReentrantLock();
    private static final HttpClient client = HttpClient.newHttpClient();
    private String task;
    private Integer vendorId;
    private String vendorJson;
    private String vendorEmail;

    public VendorService(String task, Integer vendorId, String vendorJson, String vendorEmail) {
        this.task = task;
        this.vendorId = vendorId;
        this.vendorJson = vendorJson;
        this.vendorEmail = vendorEmail;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            switch (task) {
                case "fetchAll":
                    fetchAllVendors();
                    break;
                case "fetchById":
                    if (vendorId != null) {
                        fetchVendorById(vendorId);
                    }
                    break;
                case "fetchByEmail":
                    if (vendorEmail != null) {
                        fetchVendorIdByEmail(vendorEmail);
                    }
                    break;
                case "save":
                    if (vendorJson != null) {
                        saveVendor(vendorJson);
                    }
                    break;
                case "delete":
                    if (vendorId != null) {
                        deleteVendor(vendorId);
                    }
                    break;
                default:
                    System.out.println("Invalid task specified.");
            }
        } finally {
            lock.unlock();
        }
    }

    public void fetchAllVendors() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/vendor"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String jsonResponse = response.body().trim();

            if (jsonResponse.startsWith("[") && jsonResponse.endsWith("]")) {
                jsonResponse = jsonResponse.substring(1, jsonResponse.length() - 1);
            }

            String[] vendors = jsonResponse.split("},\\s*\\{");
            System.out.println("\n----------------------------------------------------------------------------------------");
            System.out.printf("%-15s %-25s %-20s %-20s\n", "Vendor ID", "Vendor Name", "Vendor Contact", "Vendor Email");
            System.out.println("----------------------------------------------------------------------------------------");

            for (String vendor : vendors) {
                vendor = "{" + vendor.replaceAll("^[{\\[]|[}\\]]$", "") + "}";
                String[] fields = vendor.split(",\\s*\"");

                String vendorId = "", vendorName = "", vendorContact = "", vendorEmail = "";
                for (String field : fields) {
                    String[] keyValue = field.split(":\\s*");
                    if (keyValue.length == 2) {
                        String key = keyValue[0].replaceAll("[\"{}]", "").trim();
                        String value = keyValue[1].replaceAll("[\"{}]", "").trim();

                        // Match keys with vendor details
                        switch (key) {
                            case "vendorId":
                                vendorId = value;
                                break;
                            case "vendorName":
                                vendorName = value;
                                break;
                            case "vendorContact":
                                vendorContact = value;
                                break;
                            case "vendorEmail":
                                vendorEmail = value;
                                break;
                        }
                    }
                }

                // Print vendor details in a tabular format
                System.out.printf("%-15s %-25s %-20s %-20s\n", vendorId, vendorName, vendorContact, vendorEmail);
            }
            System.out.println("----------------------------------------------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchVendorById(int vendorId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/vendor/" + vendorId))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Vendor Data by ID: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String fetchVendorIdByEmail(String vendorEmail) {
        try {
            // Build and send the HTTP request to get the vendor ID by email
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/vendor/email-id/" + vendorEmail))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Assuming the response body contains only the vendor ID, as an integer (e.g., "1")
            return response.body().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Or any error handling logic
        }
    }

    public void saveVendor(String vendorJson) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/vendor"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(vendorJson))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Saved Vendor: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteVendor(int vendorId) {
        lock.lock();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8080/api/vendor/" + vendorId))
                    .DELETE()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Vendor Deletion Success.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
