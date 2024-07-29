package services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Bundle extends ResourceBundle {

    private final Properties properties = new Properties();

    public Bundle(String lan) throws FileNotFoundException {
        File dir = Arrays.stream(Objects.requireNonNull(
                        getDownloadDirectory().listFiles(file -> file.getName().startsWith(lan.toUpperCase()))))
                .findFirst()
                .orElseThrow(() -> new FileNotFoundException("Language file was not found with this parameter: " + lan.toUpperCase()));

        File file = new File(dir, "strings.properties");

        if (!file.isFile()) {
            throw new FileNotFoundException("File: " + file.getName() + " was not found.");
        }

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load properties", e);
        }
    }

    @Override
    protected Object handleGetObject(String key) {
        return properties.getProperty(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return null;
    }

    // Dummy method for illustration; replace with actual implementation
    private static File getDownloadDirectory() {
        // Implement this method to return the directory where the properties files are stored
        return new File("C:\\Users\\User\\IdeaProjects\\My-Credo\\src\\main\\resources\\resource");
    }
}