package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Consumer;

public class BasePage<T extends BasePage<T>> {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitFor(WebElement element, int sec) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(sec)); // 10 seconds timeout
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitFor(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds timeout
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    @SuppressWarnings("unchecked")
    private final T _self = (T) this;

    public T job(Consumer<T> consumer) {
        consumer.accept(_self);

        return _self;
    }

    public T loadPage(Class<T> klass) {
        try {
            return klass.getDeclaredConstructor(WebDriver.class).newInstance(driver);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create page instance", e);
        }
    }
}