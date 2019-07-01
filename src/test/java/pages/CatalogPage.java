package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class CatalogPage extends Page {

    @FindBy(xpath="//a[text()='по цене']")
    private WebElement sortByPrice;

    @FindBy(xpath="//span[text()='Сравнение']/..")
    private WebElement comparisonButton;

    @FindBy(xpath="//*[@class='preloadable__preloader']")
    private List<WebElement> preloaders;

    public WebElement firstItemInCatalog() {
        return driver.findElement(By.xpath("//a[img[@class='image']][1]"));
    }

    public WebElement maker(String maker) {
        return driver.findElement(By.xpath(String.format("//fieldset[@data-autotest-id='7893318']//li[contains(., '%s')]//label", maker)));
    }

    public WebElement firstItemForMaker(String maker) {
        return driver.findElement(By.xpath(String.format("//a[img[@class='image'] and contains(@title, '%s')]", maker)));
    }

    public WebElement addToComparisonPopup(String product) {
        return driver.findElement(By.xpath(String.format("//div[@class='popup-informer__title' and text() = 'Товар %s добавлен к сравнению']", product)));
    }

    public CatalogPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
    }

    public void goToComparison() {
        ((JavascriptExecutor)driver).executeScript("scroll(0,0)");
        comparisonButton.click();
    }

    public void selectMakers(String... makers) {
        for(String makerName : makers) {
            maker(makerName).click();
        }
        waitForCatalogLoading();
    }

    public void waitForCatalogLoading() {
        WebElement element = firstItemInCatalog();
        wait.until(stalenessOf(element));
        wait.until(not(d -> preloaders.size() > 0 ? preloaders : null));
    }

    public String addFirstItemToComparison(String maker) {
        new Actions(driver).moveToElement(firstItemForMaker(maker)).moveToElement(firstItemForMaker(maker))
                .moveToElement(firstItemForMaker(maker).findElement(By.xpath("..//i[@class='image image_name_compare']")))
                .click().perform();
        return firstItemForMaker(maker).getAttribute("title");
    }

    public boolean isPopupDisplayed(String text) {
        try{
            wait.until(visibilityOf(addToComparisonPopup(text)));
            return true;
        }
        catch(TimeoutException e) {
            return false;
        }
    }

    public void sortBy(SortBy sorting) {
        switch(sorting) {
            case PRICE:
                sortByPrice.click();
                break;
        }
        waitForCatalogLoading();
    }

    public enum SortBy {
        PRICE,
        RATING,
        REVIEWS,
        DISCOUNT,
        NEWNESS
    }
}
