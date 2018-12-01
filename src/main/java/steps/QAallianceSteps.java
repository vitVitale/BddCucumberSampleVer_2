package steps;

import cucumber.api.DataTable;
import pages.QAalliancePage;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by VITALIY on 08.11.2017.
 */
public class QAallianceSteps {

    QAalliancePage page = new QAalliancePage();

    @Step("Жмем на раздел подписчики")
    public void subscribersOfGroup(){
        page.toSubscribers();
    }

    @Step("Получаем всех пользователей")
    public void massOfSubs(){
        page.getAllSubscribers();
    }

    @Step("Запишем всех пользователей в файл")
    public void toFillFile(DataTable table){
        table.asMap(String.class, String.class)
                .forEach((field, value) -> page.writeSavedSubsIntoCSV(field, value));
    }
}
