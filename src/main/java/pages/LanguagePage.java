package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import services.Bundle;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

public class LanguagePage extends BasePage<LanguagePage> {
    public LanguagePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(className = "sub-language")
    private List<WebElement> georgianDialects;

    public List<WebElement> getGeorgianDialects() {
        waitFor(georgianDialects, 4);
        return georgianDialects;
    }

    public WebElement findDialectElementByName(String elementName) {
        return getGeorgianDialects()
                .stream()
                .filter(it -> Objects.equals(it.findElement(By.className("paragraph-14")).getText(), elementName))
                .findAny()
                .orElse(getGeorgianDialects().get(0));
    }

    public LoginPage clickKa() throws FileNotFoundException {
        return clickDialect("ka");
    }

    public LoginPage clickMeg() throws FileNotFoundException {
        return clickDialect("meg");
    }

    public LoginPage clickSvan() throws FileNotFoundException {
        return clickDialect("svan");
    }

    public LoginPage clickDialect(String dialect) throws FileNotFoundException {
        String str = new Bundle(dialect).getString("geo_dialect");
        findDialectElementByName(str).click();
        return loadPage(LoginPage.class);
    }
}
