package pages;

import io.qameta.allure.Step;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends Page<LoginPage> {

    @FindBy(id = "userName")
    private WebElement usernameField;

    @FindBy(id = "newPass")
    private WebElement passwordField;

    @FindBy(id = "submitAuth")
    private WebElement loginButton;

    @FindBy(id = "growlText")
    private WebElement warningMessage;
    @FindBy(id = "languageSwitcherBtn")
    private WebElement languageSwitcherBtn;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Enter username")
    public void enterUsername(@NonNull String username) {
        waitFor(usernameField, 5);
        usernameField.sendKeys(username);
    }

    @Step("clear User Name")
    public void clearUserName() {
        waitFor(usernameField, 5);
        Actions actions = new Actions(driver);
        actions.moveToElement(usernameField).click().keyDown(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).keyUp(Keys.CONTROL).perform();

    }

    public String getUsername() {
        waitFor(usernameField, 5);
        return usernameField.getText();
    }

    @Step("Enter password")
    public void enterPassword(@NonNull String password) {
        waitFor(passwordField, 5);
        passwordField.sendKeys(password);
    }

    public String getWarningMessage() {
        waitFor(warningMessage, 3);
        return warningMessage.getText();
    }

    @Step("Click Language Button")
    public LanguagePage clickLanguageButton() {
        waitFor(languageSwitcherBtn, 3);
        languageSwitcherBtn.click();
        return loadPage(LanguagePage.class);

    }

    public HomePage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return clickLogin();
    }

    @Step("Click Login")
    public HomePage clickLogin() {
        loginButton.click();
        return new HomePage(driver);
    }

    public boolean isSubmitDisabled() {
        return loginButton.isEnabled();
    }

    @Step("Click Login with return a page")
    public <T extends Page<T>> T clickLogin(Class<T> klass) {
        loginButton.click();
        try {
            return klass.getDeclaredConstructor(WebDriver.class).newInstance(driver);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create page instance", e);
        }
    }

}
