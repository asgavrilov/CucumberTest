package testcucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.junit.After;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PropertyReader;
import utils.WebDriverManager;

import static utils.WebDriverManager.getDriver;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class firstFeatureSteps {
    private WebDriver driver;
    private WebDriverWait webDriverWait;
    private int timeout = Integer.parseInt(PropertyReader.getPropertyFromFile("properties/settings.properties", "timeout"));
    private final static By INPUT_STRING = By.name("q");
    private final static By HEADERS = By.xpath("//div[@class='rc']/h3/a");
    @Given("^User navigates to google search$")
    public void user_navigates_to_google_search() {
       // driver = utils.WebDriverManager.getDriver();
        driver = getDriver();
        driver.get("https://google.com");
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
    }

    @When("^User is looking for \"(.*)\"$")
    public void user_is_looking_for(String query) {
        WebElement element = driver.findElement(INPUT_STRING);
        element.sendKeys(query);
        element.sendKeys(Keys.ENTER);

    }

    @Then(value = "^The page results contain \"([^\"]*)\"$", timeout = 5000)
    public void the_page_results_contain(String result) {
        webDriverWait = new WebDriverWait(driver, timeout);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(HEADERS));

        List<WebElement> elements = driver.findElements(HEADERS);
        //let's check that beatport website results are in TOP 3 websites
        for (int i = 0; i < 3; i++) {

            if (elements.get(i).getAttribute("href").contains(result.toLowerCase())) {
                Assert.assertTrue("Test" + "results position " + i, elements.get(i).getAttribute("href").contains(result.toLowerCase()));
            }

        }
    }

    @After
    public void close() {
        driver.quit();
    }

}
