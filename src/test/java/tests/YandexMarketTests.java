package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import static pages.ProductsComparisonPage.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;
import java.util.concurrent.TimeUnit;

public class YandexMarketTests {

    public WebDriver driver;
    public WebDriverWait wait;
    public CatalogPage catalogPage;
    public ElectronicsPage electronicsPage;
    public HomePage homePage;
    public ProductsComparisonPage productsComparisonPage;

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setupTest() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 4);
        driver.manage().window().maximize();
        catalogPage = new CatalogPage(driver);
        electronicsPage = new ElectronicsPage(driver);
        homePage = new HomePage(driver);
        productsComparisonPage = new ProductsComparisonPage(driver);
    }

    @Test
    public void test() {
        homePage.open();
        homePage.goToElectronics();
        electronicsPage.goToPhonesCatalogPage();
        catalogPage.selectMakers("Meizu", "Xiaomi");
        catalogPage.sortBy(CatalogPage.SortBy.PRICE);
        String title1 = catalogPage.addFirstItemToComparison("Meizu");
        Assert.assertTrue(catalogPage.isPopupDisplayed(title1));
        String title2 = catalogPage.addFirstItemToComparison("Xiaomi");
        Assert.assertTrue(catalogPage.isPopupDisplayed(title2));
        catalogPage.goToComparison();
        Assert.assertEquals(productsComparisonPage.getProductsCount(), 2);
        productsComparisonPage.showCharacteristics(Characteristics.ALL);
        Assert.assertTrue(productsComparisonPage.isCharacteristicDisplayed("Операционная система"));
        productsComparisonPage.showCharacteristics(Characteristics.DIFFERING);
        Assert.assertFalse(productsComparisonPage.isCharacteristicDisplayed("Операционная система"));
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

}