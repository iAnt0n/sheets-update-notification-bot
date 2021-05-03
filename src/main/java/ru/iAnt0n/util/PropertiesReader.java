package ru.iAnt0n.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesReader {
    private final static String propertiesPath = "config.properties";

    public static String getAPIKey() {

        try (InputStream input = new FileInputStream(propertiesPath)) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty("key");
        }
        catch (Exception e){
            e.printStackTrace();
            return "NO_KEY";
        }
    }
}
