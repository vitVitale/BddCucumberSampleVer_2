package steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import utills.TestProps;

import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by VITALIY on 27.10.2017.
 */
public class BaseSteps {

    private static String baseUrl;
    private static Properties properties = TestProps.getInstance().getProperties();
    private static HashMap<String, String> variables = new HashMap<>();
    public static WebDriver driver;

    @Before
    public void before() throws Exception{
        switch (properties.getProperty("browser.name")){
            case "Firefox":
                System.setProperty("webdriver.gecko.driver", properties.getProperty("webdriver.gecko.driver"));
                driver = new FirefoxDriver();
                break;
            case "Chrome":
                System.setProperty("webdriver.chrome.driver", properties.getProperty("webdriver.chrome.driver"));
                driver = new ChromeDriver();
                break;
        }
        baseUrl = properties.getProperty("webdriver.url");
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        //driver.manage().window().maximize();
        driver.get(baseUrl);
    }

    @After
    public void after() {

        driver.quit();
    }

    public static WebDriver getDriver() {

        return driver;
    }
}
