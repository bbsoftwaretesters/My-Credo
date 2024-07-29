package pages;

import org.openqa.selenium.WebElement;

public abstract class ElementComponent {
    protected WebElement element;

    public ElementComponent(WebElement element) {
        this.element = element;
    }
}
