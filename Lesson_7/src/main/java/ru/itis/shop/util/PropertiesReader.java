package ru.itis.shop.util;

import ru.itis.shop.app.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private final String fileName;

    public PropertiesReader(String fileName) {
        this.fileName = fileName;
    }

    public Properties loadProperties() {
        Properties properties = new Properties();

        try {
            InputStream input = Main.class.getClassLoader().getResourceAsStream(fileName);
            properties.load(input);

            System.out.println(properties.getProperty("db.url"));
            System.out.println(properties.getProperty("db.user"));
            System.out.println(properties.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        return properties;
    }
}
