package Server;

import java.io.FileInputStream;
import java.util.Properties;

public class EnvironmentManager {
    public static String getBaseUrl(String env) {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("src/test/resources/config/" + env + ".properties"));
            return props.getProperty("baseUrl");
        } catch (Exception e) {
            throw new RuntimeException("Failed to load environment config", e);
        }
    }
}