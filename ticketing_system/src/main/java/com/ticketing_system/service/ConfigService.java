package com.ticketing_system.service;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

@Service
public class ConfigService {

    private static final String CONFIG_FILE = "config.properties";

    public void updateProperty(String key, String value) {
        Properties properties = new Properties();

        // Load the properties file from classpath
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                System.out.println("Sorry, unable to find " + CONFIG_FILE);
                return;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Update the property
        properties.setProperty(key, value);

        // Save the properties file
        try {
            // Get the absolute path to save the file
            String path = Paths.get(getClass().getClassLoader().getResource(CONFIG_FILE).toURI()).toFile().getAbsolutePath();
            try (FileOutputStream output = new FileOutputStream(path)) {
                properties.store(output, null);
                System.out.println("Property " + key + " updated to " + value);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}