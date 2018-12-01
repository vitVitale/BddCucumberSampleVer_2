package pages;

import com.csvreader.CsvWriter;
import cucumber.api.DataTable;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import steps.BaseSteps;
import utills.ActionTitle;
import utills.PageEntry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by VITALIY on 08.11.2017.
 */
@PageEntry(title = "Группа QA альянс")
public class QAalliancePage extends BasePage {

    private static QAalliancePage INSTANCE;
    private static QAalliancePage getInstance(){
        if (INSTANCE == null){
            INSTANCE = new QAalliancePage();
        }
        return INSTANCE;
    }

    @FindBy(xpath = "//SPAN[@class='header_label fl_l'][text()='Подписчики']")
    private WebElement subscribersBTN;

    @FindBy(xpath = "//DIV[@class='_scroll_node fans_box']")
    private WebElement scrollLayer;

    @FindBy(xpath = "//DIV[@id='fans_rowsmembers']/*")
    private List<WebElement> listOfFans;

    public WebElement getSubscribersBTN() {
        return subscribersBTN;
    }

    public WebElement getScrollLayer() {
        return scrollLayer;
    }

    public List<WebElement> getListOfFans() {
        return listOfFans;
    }

    public QAalliancePage(){
        driver = BaseSteps.getDriver();
        PageFactory.initElements(driver, this);
    }

    @ActionTitle(value = "нажимает раздел подписчиков и получение всех")
    public void subscribersOfGroup(){
        getInstance().toSubscribers();
        getInstance().getAllSubscribers();
    }

    @ActionTitle(value = "запишем всех пользователей в файл")
    public void toFillFile(DataTable table){
        table.asMap(String.class, String.class)
                .forEach((field, value) -> getInstance().writeSavedSubsIntoCSV(field, value));
    }

    public void toSubscribers(){
        driver.get("https://vk.com/qa_alliance");
        clicker(getSubscribersBTN());
    }

    public void getAllSubscribers(){

        for (int i = 0; i < 3; i++)            //взято мало элементов т к компьютер слабый - долго обрабатывает,
                                                // но если нужны все 18 000 подписчиков - то можно просто увеличить i  в 100 - 200 раз
        {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", getScrollLayer());
            try {
                Thread.sleep(333);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Wait<WebDriver> wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOfAllElements(getListOfFans()));
        System.out.println("-----------||--------  "+getListOfFans().size()+"  --------||---------");
    }

    public void writeSavedSubsIntoCSV(String element, String value)         //перед каждым запуском желательно удалять созданный CSV файл
                                                                            //или указывать новые имена, чтобы значения не дописывались в предыдущий.
    {
        if (element.equals("Имя файла")) {
            String csvOutputFile = value;
            boolean isFileExist = new File(csvOutputFile).exists();
            try {

                CsvWriter testcases = new CsvWriter(new FileWriter(csvOutputFile, true), ',');

                if (!isFileExist) {
                    testcases.write(" Id ");
                    testcases.write("Имя Фамилия");

                    testcases.endRecord();
                }
                String strOut;
                for (WebElement we : getListOfFans()) {
                    testcases.write(we.getAttribute("data-id"));
                    strOut = we.getText().replaceAll("\\n", " ");
                    testcases.write(strOut);

                    testcases.endRecord();
                }
                testcases.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
