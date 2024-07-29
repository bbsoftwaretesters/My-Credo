package com.myCredo.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import utils.StringUtils;

import java.util.Random;

public class MyCredoTest {

    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @DataProvider(name = "loginData")
    public Object[][] createLoginData() {
        Object[][] data = new Object[10][2]; // Array to hold the test data

        for (int i = 0; i < 10; i++) {
            int randomInt = new Random().nextInt((10 - 1) + 1) + 1;
            String randomStr = RandomStringUtils.random(randomInt, StringUtils.enChars);
            data[i][0] = randomStr;
            data[i][1] = randomStr;
        }

        return data;
    }

    @Test(dataProvider = "loginData", threadPoolSize = 5)
    public void loginNegativeTest(String username, String password) {
        SoftAssert softAssert = new SoftAssert();

        // Initialize WebDriver inside the test method
        driver.get("https://mycredo.ge/landing/main/auth");
        System.out.println("Generated password: " + password);

        new LoginPage(driver)
                .job(l -> {
                    l.enterUsername(username);
                    System.out.println("entering Username: " + username);
                    System.out.println("entered Username: " + l.getUsername());
                    l.enterPassword(password);
                })
                .clickLogin(LoginPage.class)
                .job(l -> {
                    String warningMessage = l.getWarningMessage();
                    softAssert.assertEquals(warningMessage, "მონაცემები არასწორია", warningMessage + " warning message is not found");
                });

        softAssert.assertAll();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
