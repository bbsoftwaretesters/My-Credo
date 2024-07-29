package com.myCredo.testsUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TestUtils {
    public static WebDriver driver;

    public byte[] takeScreenshot() {
        if (driver instanceof TakesScreenshot) {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        }
        throw new RuntimeException("Driver does not support taking screenshots");
    }

    private Properties loadAllureProperties() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/allure.properties")) {
            properties.load(fis);
        }
        return properties;
    }

    public void setAllureEnvironment() {
        Map<String, String> map = new HashMap<>();
        map.put("Environment", utils.Properties.getInstance().getProperty("environment", ""));

        try {
            createAllureEnvironment(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createAllureEnvironment(Map<String, String> parameters) throws Exception {
        Properties properties = loadAllureProperties();
        String resultsDirectory = properties.getProperty("allure.results.directory");
        File resultsDir = new File(resultsDirectory);
        if (!resultsDir.exists()) {
            if (!resultsDir.mkdirs()) {
                throw new IOException("Failed to create directory: " + resultsDirectory);
            }
        }

        File outputFile = new File(resultsDirectory + "/environment.xml");

        StringWriter writer = new StringWriter();
        XMLOutputFactory xmlFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter xmlStreamWriter = xmlFactory.createXMLStreamWriter(writer);

        try {
            xmlStreamWriter.writeStartDocument("UTF-8", "1.0");
            xmlStreamWriter.writeStartElement("environment");

            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                xmlStreamWriter.writeStartElement("parameter");
                xmlStreamWriter.writeStartElement("key");
                xmlStreamWriter.writeCharacters(entry.getKey());
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeStartElement("value");
                xmlStreamWriter.writeCharacters(entry.getValue());
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeEndElement();
            }

            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeEndDocument();
        } finally {
            xmlStreamWriter.close();
        }

        try (FileWriter fileWriter = new FileWriter(outputFile)) {
            fileWriter.write(writer.toString());
        }
    }
}