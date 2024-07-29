package com.myCredo.tests;

import com.myCredo.testsUtils.AuthenticationTestUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;

import java.io.FileNotFoundException;

public class AuthenticationTest extends AuthenticationTestUtils {

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @BeforeMethod
    public void loadAuthPage() {
        driver.get("https://mycredo.ge/landing/main/auth");
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

        new LoginPage(driver)
                .clickLanguageButton()
                .clickDialect(dialect)
                .job(it -> fillAuthFields(it, username, password))
                .clickLogin(LoginPage.class)
                .job(it -> checkWarningMessage(it, dialect, softAssert));

        softAssert.assertAll();
    }

    @Test(dataProvider = "submitData", threadPoolSize = 1)
    public void submitButtonNegativeTest(String randomStr, String dialect) throws FileNotFoundException {
        SoftAssert softAssert = new SoftAssert();

        new LoginPage(driver)
                .clickLanguageButton()
                .clickDialect(dialect)
                .job(it -> it.enterUsername(randomStr))
                .job(it -> softAssert.assertFalse(it.isSubmitDisabled()))
                .job(LoginPage::clearUserName)
                .job(it -> it.enterPassword(randomStr))
                .job(it -> softAssert.assertFalse(it.isSubmitDisabled()));

        softAssert.assertAll();
    }
}
