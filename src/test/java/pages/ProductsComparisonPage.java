package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import java.util.List;

public class ProductsComparisonPage extends Page {

    @FindBy(css=".price")
    private List<WebElement> prices;

    @FindBy(xpath="//span[text()='все характеристики']")
    private WebElement allCharacteristicsButton;

    @FindBy(xpath="//span[text()='различающиеся характеристики']")
    private WebElement differingCharacteristicsButton;

    public WebElement characteristic(String characteristicName) {
        return driver.findElement(By.xpath(String.format("//div[contains(@class, 'n-compare-row') and text()='%s']", characteristicName)));
    }

    public ProductsComparisonPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 4);
        PageFactory.initElements(driver, this);
    }

    public int getProductsCount() {
        return prices.size();
    }

    public void showCharacteristics(Characteristics characteristics) {
        if(characteristics == Characteristics.ALL)
            allCharacteristicsButton.click();
        if(characteristics == Characteristics.DIFFERING)
            differingCharacteristicsButton.click();
    }

    public boolean isCharacteristicDisplayed(String characteristicName) {
        try {
            wait.until(visibilityOf(characteristic(characteristicName)));
            return true;
        }
        catch(TimeoutException e) {
            return false;
        }
    }

    public enum Characteristics {
        ALL,
        DIFFERING
    }
}
