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
import services.Bundle;
import utils.StringUtils;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MyCredoTest {

    private WebDriver driver;

    final List<String> dialects = Arrays.asList("ka", "meg", "svan");

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @DataProvider(name = "loginData")
    public Object[][] createLoginData() {
        int count = dialects.size();
        Object[][] data = new Object[count][count]; // Array to hold the test data

        for (int i = 0; i < count; i++) {
            int randomInt = new Random().nextInt((10 - 1) + 1) + 1;
            String randomStr = RandomStringUtils.random(randomInt, StringUtils.enChars);
            data[i][0] = randomStr;
            data[i][1] = randomStr;
            data[i][2] = dialects.get(i);
        }

        return data;
    }

    @Test(dataProvider = "loginData", threadPoolSize = 1)
    public void loginNegativeTest(String username, String password, String dialect) throws FileNotFoundException {
        SoftAssert softAssert = new SoftAssert();

        // Initialize WebDriver inside the test method
        driver.get("https://mycredo.ge/landing/main/auth");

        new LoginPage(driver)
                .clickLanguageButton()
                .clickDialect(dialect)
                .job(it -> {
                    it.enterUsername(username);
                    it.enterPassword(password);
                })
                .clickLogin(LoginPage.class)
                .job(it -> checkWarningMessage(it, dialect, softAssert));

        softAssert.assertAll();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void test1() throws FileNotFoundException {
        String str = new Bundle("meg").getString("login_submit");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(str, "შესვლა");
        softAssert.assertAll();
    }

    private static void checkWarningMessage(LoginPage l, String dialect, SoftAssert softAssert) {
        String warningMessage = l.getWarningMessage();
        try {
            String str = new Bundle(dialect).getString("warning_message");
            softAssert.assertEquals(warningMessage, str, warningMessage + " warning message is not found");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
