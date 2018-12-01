package pages;

import cucumber.api.DataTable;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import steps.BaseSteps;
import utills.ActionTitle;
import utills.PageEntry;

/**
 * Created by VITALIY on 07.11.2017.
 */
@PageEntry(title = "Персональная страница")
public class VKpersonalPage extends BasePage {

    private static VKpersonalPage INSTANCE;
    private static VKpersonalPage getInstance(){
        if (INSTANCE == null){
            INSTANCE = new VKpersonalPage();
        }
        return INSTANCE;
    }

    @FindBy(id = "l_gr")
    private WebElement l_groupsBTN;

    @FindBy(xpath = ".//*[contains(text(),'Создать сообщество')]")
    private WebElement groups_create_btn;

    @FindBy(xpath = ".//*[contains(text(),'Бизнес')]")
    private WebElement group_create_Biesness_plate;

    @FindBy(id = "groups_create_box_title")
    private WebElement group_create_titleFLD;

    @FindBy(id = "groups_create_group")
    private WebElement groups_create_groupCHBX;

    @FindBy(xpath = ".//div[@id='container2']//input[@type='text']")
    private WebElement dropdown1SLT;

    @FindBy(id = "groups_create_box_site")
    private WebElement dropdown2SLT;

    @FindBy(xpath = "(//BUTTON[@class='flat_button'][text()='Создать сообщество'][text()='Создать сообщество'])[1]")
    private WebElement createGroupBTN;

    @FindBy(xpath = "(//BUTTON[@class='flat_button group_save_button'])[1]")
    private WebElement saveGroupBTN;

    public WebElement getL_groupsBTN() {
        return l_groupsBTN;
    }

    public WebElement getgroup_create_Biesness_plate() {
        return group_create_Biesness_plate;
    }

    public WebElement getGroups_create_btn() {
        return groups_create_btn;
    }

    public WebElement getGroup_create_titleFLD() {
        return group_create_titleFLD;
    }

    public WebElement getGroups_create_groupCHBX() {
        return groups_create_groupCHBX;
    }

    public WebElement getDropdown1SLT() {
        return dropdown1SLT;
    }

    public WebElement getDropdown2SLT() {
        return dropdown2SLT;
    }

    public WebElement getCreateGroupBTN() {
        return createGroupBTN;
    }

    public WebElement getSaveGroupBTN() {
        return saveGroupBTN;
    }

    public VKpersonalPage(){
        driver = BaseSteps.getDriver();
        PageFactory.initElements(driver, this);
    }

    @ActionTitle(value = "нажимает на создание группы")
    public void groupsClick(){
        clicker(getL_groupsBTN());
        clicker(getGroups_create_btn());
        clicker(getgroup_create_Biesness_plate());
    }

    @ActionTitle(value = "создает группу с параметрами")
    public void groupCreate(DataTable table){
        table.asMap(String.class, String.class)
                .forEach((field, value) -> getInstance().creatorGroup(field, value));
    }

    @ActionTitle(value = "НЕ нажимает на создание группы")
    public void groupBtn(){
        getInstance().groupsClick("apln_test1");
    }

    @ActionTitle(value = "выбирает друга")
    public void groupCreate(String friendName){
        clicker(driver.findElement(By.id("l_fr")));
        clicker(driver.findElement(By.xpath(".//div[@id='friends_list']//a[contains(text(),'"+friendName+"')]")));
        Assert.assertTrue("Открыта не та страница!",driver.findElement(By.xpath(".//h2[@class='page_name']")).getText().equals(friendName));
    }

    private void groupsClick(String groupName){
        clicker(getL_groupsBTN());
        clicker(driver.findElement(By.xpath(".//div[@class='group_row_labeled']//a[text()='"+groupName+"']")));
    }

    private void creatorGroup(String element, String value)  {
        switch (element) {
            case "Название":
                fillField(getGroup_create_titleFLD(), value);
                break;
            case "Выберите тематику":
                fillField(getDropdown1SLT(), value);
                getDropdown1SLT().sendKeys(Keys.ENTER);
                break;
            case "Веб-сайт":
                fillField(getDropdown2SLT(), value);
                getDropdown2SLT().sendKeys(Keys.ENTER);
                clicker(getCreateGroupBTN());
                clicker(getSaveGroupBTN());
                break;
        }
    }
}
