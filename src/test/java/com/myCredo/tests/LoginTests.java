package com.myCredo.tests;

import com.myCredo.testsUtils.LoginTestUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import services.Bundle;

import java.io.FileNotFoundException;

public class LoginTests extends LoginTestUtils {

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(dataProvider = "loginData", threadPoolSize = 1)
    public void loginNegativeTest(String username, String password, String dialect) throws FileNotFoundException {
        SoftAssert softAssert = new SoftAssert();

        // Initialize WebDriver inside the test method
        driver.get("https://mycredo.ge/landing/main/auth");

        new LoginPage(driver)
                .clickLanguageButton()
                .clickDialect(dialect)
                .job(it -> fillAuthFields(it, username, password))
                .clickLogin(LoginPage.class)
                .job(it -> checkWarningMessage(it, dialect, softAssert));

        softAssert.assertAll();
    }

    @Test
    public void test1() throws FileNotFoundException {
        String str = new Bundle("meg").getString("login_submit");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(str, "შესვლა");
        softAssert.assertAll();
    }

}
