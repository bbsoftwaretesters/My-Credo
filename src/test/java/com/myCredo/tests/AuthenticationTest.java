package com.myCredo.tests;

import com.myCredo.testsUtils.AuthenticationTestUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import utils.AllureUtils;

import java.io.FileNotFoundException;

@Epic("Authentication")
public class AuthenticationTest extends AuthenticationTestUtils {

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        setAllureEnvironment();
    }

    @BeforeMethod
    public void loadAuthPage() {
        driver.get("https://mycredo.ge/landing/main/auth");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            AllureUtils.stepTakeScreenshot(takeScreenshot());
            driver.quit();
        }
    }

    @Feature("negative Auth")
    @Story("User enters incorrect username and password, clicks login, system displays warning message")
    @Description("Warning test of message")
    @Test(dataProvider = "loginData", threadPoolSize = 1)
    public void loginNegativeTest(String username, String password, String dialect) throws FileNotFoundException {
        SoftAssert softAssert = new SoftAssert();

        new LoginPage(driver)
                .clickLanguageButton()
                .clickDialect(dialect)
                .job(it -> fillAuthFields(it, username, password))
                .clickLogin(LoginPage.class)
                .job(it -> checkWarningMessage(it, dialect, softAssert))
                .job(AllureUtils::stepTakeScreenshot);

        softAssert.assertAll();
    }

    @Feature("negative Auth")
    @Story("While both username and password field is not filled, login button is disable")
    @Description("Button activity validation")
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
                .job(it -> softAssert.assertFalse(it.isSubmitDisabled()))
                .job(AllureUtils::stepTakeScreenshot);

        softAssert.assertAll();
    }
}
