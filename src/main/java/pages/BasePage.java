package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import utills.Page;
import utills.PageEntry;
import utills.Stash;

import java.util.Map;


@PageEntry(title = "Базовая страница")
public abstract class BasePage extends Page {
    WebDriver driver;

    public BasePage(){
    }

    public static Map<String, Object> getStash(){
        return Stash.getInstance();
    }

    public void clicker(WebElement element){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
        Wait<WebDriver> wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
    }

    public void fillField(WebElement element, String value)  {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
        element.click();
        element.sendKeys(Keys.chord(Keys.CONTROL,"a"));
        element.sendKeys(value);
    }

    public void clickForCheckBox(WebElement element){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
        if (!element.isSelected()){
            ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
        }
    }
}
