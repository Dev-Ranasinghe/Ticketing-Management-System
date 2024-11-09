package com.ticketing_system.service;

import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Service
public class ConfigService {

    public ConfigService() {
        try {
            loadProperties();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String CONFIG_FILE = "config.properties";
    Properties properties = new Properties();

    public void updateProperty(String key, String value) throws URISyntaxException {
        Path configPath = Path.of(Paths.get(getClass().getClassLoader().getResource(CONFIG_FILE).toURI()).toFile().getAbsolutePath());; // Specify the external path here

        // Update the property
        properties.setProperty(key, value);

        // Save the properties file back to the external location
        try (FileOutputStream output = new FileOutputStream(configPath.toFile())) {
            properties.store(output, null);
            System.out.println("Property " + key + " updated to " + value);
            loadProperties();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Failed to save the properties file to " + configPath);
        }
    }

    private void loadProperties() throws URISyntaxException {

        Path configPath = Path.of(Paths.get(getClass().getClassLoader().getResource(CONFIG_FILE).toURI()).toFile().getAbsolutePath());; // Specify the external path here

        // Load the properties file from the external location
        try (FileInputStream input = new FileInputStream(configPath.toFile())) {
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Failed to load the properties file from " + configPath);
            return;
        }
    }

    public String getTotalTickets() {
        return properties.getProperty("totalTickets");
    }

    public String getMaxTicketCapacity() {
        return properties.getProperty("maxTicketCapacity");
    }

    public String getCustomerRetrievalRate() {
        return properties.getProperty("customerRetrievalRate");
    }

    public String getTicketReleaseRate() {
        return properties.getProperty("ticketReleaseRate");
    }
}
