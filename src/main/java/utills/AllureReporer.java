package utills;

import gherkin.formatter.model.Result;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ru.yandex.qatools.allure.Allure;
import ru.yandex.qatools.allure.cucumberjvm.AllureReporter;
import ru.yandex.qatools.allure.events.MakeAttachmentEvent;
import steps.BaseSteps;

/**
 * Created by VITALIY on 27.10.2017.
 */
public class AllureReporer extends AllureReporter{
    @Override
    public void result(Result result) {
        takeScreenshot(result);
        super.result(result);
    }


    public void takeScreenshot(Result step) {
        if (BaseSteps.getDriver() != null) {
            Allure.LIFECYCLE.fire(new MakeAttachmentEvent(((TakesScreenshot) BaseSteps.getDriver()).getScreenshotAs(OutputType.BYTES),
                    "Скриншот на каждом шаге", "image/png"));
        }
    }
}
