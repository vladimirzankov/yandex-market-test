package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class HomePage extends Page {

    @FindBy(xpath="//div[contains(@class, 'horizontal-tabs-container')]//span[text() = 'Электроника']")
    private WebElement electronicsButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 4);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://market.yandex.ru");
        wait.until(visibilityOf(electronicsButton));
    }

    public void goToElectronics() {
        electronicsButton.click();
    }

}
