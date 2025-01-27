package utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Properties {

    private java.util.Properties properties;
    private static Properties _instance;

    public static Properties getInstance() {
        if (_instance == null) {
            java.util.Properties props = new java.util.Properties();
            File file = new File(System.getProperty("user.dir"), "properties.properties");
            if (!file.isFile()) {
                file = new File(((URL) Objects.requireNonNull(Properties.class.getResource("/properties.properties"), "properties was not found create properties.properties into project directory or into resource directory")).getFile());
            }

            try {
                props.load(new FileReader(file));
                _instance = new Properties();
                _instance.properties = props;
            } catch (IOException var3) {
                IOException e = var3;
                throw new RuntimeException(e);
            }
        }

        return _instance;
    }

    public String getProperty(String key, String defaultValue) {
        return this.properties.getProperty(key, defaultValue);
    }
}
