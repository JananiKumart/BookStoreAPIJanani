package Hooks;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import Server.ExtentReportUtil;

import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static io.restassured.RestAssured.baseURI;

public class CucumberHooks {
    @Before
    public  void setup() {
        baseURI="http://127.0.0.1:8000";
        ExtentReportUtil.initReports();
    }

    @AfterAll
    public static void tearDown() {
        ExtentReportUtil.flushReports();
    }
}
