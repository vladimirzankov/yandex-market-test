package pages;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElectronicsPage extends Page {

    @FindBy(xpath="//ul[@data-autotest-id='subItems']//a[text() = 'Мобильные телефоны']")
    private WebElement cellPhonesButton;

    public ElectronicsPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 4);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://market.yandex.ru/catalog--elektronika/54440");
    }

    public void goToPhonesCatalogPage() {
        cellPhonesButton.click();
    }
}
