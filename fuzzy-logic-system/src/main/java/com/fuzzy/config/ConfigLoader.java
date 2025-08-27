package com.fuzzy.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    public static Configuration loadConfig(String resourceName) {
        Properties properties = new Properties();
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (input == null) {
                throw new IllegalArgumentException("Resource not found: " + resourceName);
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load configuration", ex);
        }

        String property1 = properties.getProperty("property1");
        int property2 = Integer.parseInt(properties.getProperty("property2"));

        return new Configuration(property1, property2);
    }
}
