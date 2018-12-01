import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"utills.AllureReporer"},
        glue = {""},
        features = {"src/test/resources/features/"},
        tags = {"@Friends_test"}

)
public class CucumberRunner {
}
