package pages;

import cucumber.api.DataTable;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import steps.BaseSteps;
import utills.ActionTitle;
import utills.PageEntry;

/**
 * Created by VITALIY on 07.11.2017.
 */
@PageEntry(title = "Авторизация.")
public class VKlogInPage extends BasePage {

    private static VKlogInPage INSTANCE;
    private static VKlogInPage getInstance(){
        if (INSTANCE == null){
            INSTANCE = new VKlogInPage();
        }
        return INSTANCE;
    }

    @FindBy(id = "index_email")
    private WebElement index_emailFLD;

    @FindBy(id = "index_pass")
    private WebElement index_passFLD;

    @FindBy(id = "index_login_button")
    private WebElement index_login_buttonBTN;

    public WebElement getIndex_emailFLD() {
        return index_emailFLD;
    }

    public WebElement getIndex_passFLD() {
        return index_passFLD;
    }

    public WebElement getIndex_login_buttonBTN() {
        return index_login_buttonBTN;
    }

    public VKlogInPage(){
        driver = BaseSteps.getDriver();
        PageFactory.initElements(driver, this);
    }

    private void fillFieldOfLogInVK(String element, String value) {
        switch (element) {
            case "Телефон или email":
                fillField(getIndex_emailFLD(), value);
                break;
            case "Пароль":
                fillField(getIndex_passFLD(), value);
                break;
        }
    }
    private void buttonPusher(){
        getIndex_login_buttonBTN().click();
    }

    @ActionTitle(value = "авторизуется в клиенте")
    public void fillFields(DataTable table){
        table.asMap(String.class, String.class)
                .forEach((field, value) -> getInstance().fillFieldOfLogInVK(field, value));
        buttonPusher();
    }
}
