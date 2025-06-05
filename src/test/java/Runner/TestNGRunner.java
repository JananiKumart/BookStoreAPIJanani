package Runner;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        features = "src/test/resources/Features",
        glue = {"Steps","Hooks"},
        tags = "", dryRun = false)
public class TestNGRunner extends AbstractTestNGCucumberTests {

}

