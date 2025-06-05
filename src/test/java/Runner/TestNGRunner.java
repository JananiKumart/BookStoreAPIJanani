package Runner;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        features = "src/test/resources/Features",
        glue = {"Steps","Hooks"},
        plugin = {"pretty","io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        tags = "", dryRun = false)
public class TestNGRunner extends AbstractTestNGCucumberTests {

}

