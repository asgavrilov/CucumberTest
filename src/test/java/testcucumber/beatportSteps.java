package testcucumber;

import org.junit.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PropertyReader;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static utils.WebDriverManager.getDriver;

public class beatportSteps {

   // private final static By SEARCH_LINK = By.cssSelector("a[class='mobile-search-launch-link']");
    private final static By INPUT_STRING = By.cssSelector("input[role='combobox']");
    private final static By CHART_ITEM = By.cssSelector("p.chart-title");
    private final static By CHART_CONTAINTER = By.cssSelector("div.bucket.charts");
    private WebDriver driver;
    private WebDriverWait webDriverWait;
    private int timeout = Integer.parseInt(PropertyReader.getPropertyFromFile("properties/settings.properties", "timeout"));

    @Given("^open beatport.com$")
    public void openBeatport() {
        driver = getDriver();
        driver.get("https://www.beatport.com");
        driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
    }

    @When(value ="^search for \"([^\"]*)\" artist$", timeout = 5000)
    public void searchForArtist(String arg0) {

//        WebElement search = driver.findElement(SEARCH_LINK);
//        search.click();
        WebElement element = driver.findElement(INPUT_STRING);
        element.sendKeys(arg0);
        element.sendKeys(Keys.ENTER);

    }

    @Then(value = "^Charts contain \"([^\"]*)\" name$", timeout = 5000)
    public void chartsContainName(String arg0) {

        webDriverWait = new WebDriverWait(driver, timeout);
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(CHART_CONTAINTER));
        List<WebElement> chartItems = driver.findElements(CHART_ITEM);
        for (int i = 1; i < chartItems.size(); i++) {
            Assert.assertTrue(chartItems.get(i).getText().toLowerCase().contains(arg0));
        }
    }

    @After
    public void close() {
        driver.quit();
    }


}
