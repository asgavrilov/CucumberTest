package utils;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class WebDriverManager {

    private static final String PATH_TO_PROPERTIES = "properties/settings.properties";
    private static final String URL = PropertyReader.getPropertyFromFile(PATH_TO_PROPERTIES, "hub");

    private static WebDriver driver;

    public static WebDriver getDriver() {
        String browser = System.getProperty("browser");

               // PropertyReader.getPropertyFromFile(PATH_TO_PROPERTIES, "browser");

        if (driver == null ) {

            //String browserFromProperties = PropertyReader.getPropertyFromFile(PATH_TO_PROPERTIES, "browser");
            switch (browser) {

                case "mozilla":
                    createFirefoxDriver();
                    break;
                case "chrome-remote":
                    createRemoteChromeDriver();
                    break;
                case "ie":
                    createIEDriver();
                    break;
                case "chrome":
                    createChromeDriver(false);
                    break;
                case "chrome-headless":
                    createChromeDriver(true);
                    break;
                default:
                    createChromeDriver(false);
                    break;
            }
        }
        return driver;
    }

    private static WebDriver createRemoteChromeDriver() {

        try {

/*It is required to install ChromeDriver locally Before Remote webriver usage^
1. Download chromedriver
2. unzip chromedriver_linux64.zip
3. chmod +x chromedriver

4. sudo mv -f chromedriver /usr/local/share/chromedriver
5. sudo ln -s /usr/local/share/chromedriver /usr/local/bin/chromedriver
6. sudo ln -s /usr/local/share/chromedriver /usr/bin/chromedriver
*/
            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setPlatform(Platform.LINUX);
            driver = new RemoteWebDriver(new URL(URL), capabilities);
            driver.manage().window().maximize();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return driver;
    }

    private static WebDriver createChromeDriver(Boolean headless) {
        if (headless) {
            io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");

            driver = new ChromeDriver(options);

        } else {
            io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
        return driver;
    }


    private static WebDriver createFirefoxDriver() {
        io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        return driver;
    }

    private static WebDriver createIEDriver() {
        io.github.bonigarcia.wdm.WebDriverManager.iedriver().setup();
        driver = new InternetExplorerDriver();
        return driver;
    }
}
