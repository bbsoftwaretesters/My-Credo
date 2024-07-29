package com.myCredo.testsUtils;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;
import org.testng.asserts.SoftAssert;
import pages.LoginPage;
import services.Bundle;
import utils.StringUtils;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AuthenticationTestUtils extends TestUtils {

    final List<String> dialects = Arrays.asList("ka", "meg", "svan");

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

    @DataProvider(name = "submitData")
    public Object[][] createSubmitData() {
        int count = dialects.size();
        Object[][] data = new Object[count][2]; // Array to hold the test data

        for (int i = 0; i < count; i++) {
            int randomInt = new Random().nextInt((10 - 1) + 1) + 1;
            String randomStr = RandomStringUtils.random(randomInt, StringUtils.enChars);
            data[i][0] = randomStr;
            data[i][1] = dialects.get(i);
        }

        return data;
    }


    public void fillAuthFields(LoginPage page, String username, String password) {
        page.job(it -> {
            it.enterUsername(username);
            it.enterPassword(password);
        });
    }
    public static void checkWarningMessage(LoginPage l, String dialect, SoftAssert softAssert) {
        String warningMessage = l.getWarningMessage();
        try {
            String str = new Bundle(dialect).getString("warning_message");
            softAssert.assertEquals(warningMessage, str, warningMessage + " warning message is not found");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
