package pages;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;

public class Page<T extends Page<T>> {

    protected WebDriver driver;

    public Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public byte[] takeScreenshot() {
        if (driver instanceof TakesScreenshot) {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        }
        throw new RuntimeException("Driver does not support taking screenshots");
    }

    public void waitFor(WebElement element, int sec) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(sec)); // 10 seconds timeout
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitFor(List<WebElement> elements, int sec) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(sec));
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
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

    public <P extends Page<P>> P loadPage(Class<P> klass) {
        try {
            return klass.getDeclaredConstructor(WebDriver.class).newInstance(driver);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create page instance", e);
        }
    }

    public String getClassName() {
        Class<?> k = getClass();

        if (k.getSimpleName().contains("ByteBuddy")) {
            k = k.getSuperclass();
        }
        ;

        return k.getSimpleName();
    }
}
