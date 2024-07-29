package pages;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage<LoginPage> {

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

    public void enterUsername(@NonNull String username) {
        waitFor(usernameField, 5);
        usernameField.sendKeys(username);
    }

    public String getUsername() {
        waitFor(usernameField, 5);
        return usernameField.getText();
    }

    public void enterPassword(@NonNull String password) {
        waitFor(passwordField, 5);
        passwordField.sendKeys(password);
    }

    public String getWarningMessage() {
        waitFor(warningMessage, 3);
        return warningMessage.getText();
    }

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

    public HomePage clickLogin() {
        loginButton.click();
        return new HomePage(driver);
    }

    public <T extends BasePage<T>> T clickLogin(Class<T> klass) {
        loginButton.click();
        try {
            return klass.getDeclaredConstructor(WebDriver.class).newInstance(driver);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create page instance", e);
        }
    }

}
