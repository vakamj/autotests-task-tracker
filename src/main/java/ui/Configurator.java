package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * Driver configurator class
 */
public final class Configurator {

    private static WebDriver driver;

    private Configurator() {

    }

    /**
     * driver setup method
     */

    public static void setup() {
        /*WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();*/
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    /**
     * driver shut down
     */

    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static void refreshPage() {
        driver.navigate().refresh();
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
