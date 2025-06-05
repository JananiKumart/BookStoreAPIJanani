package Server;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;


public class ExtentReportUtil {
    public static ExtentReports extent;
    public static ExtentTest test;

    public static void initReports() {
        if (extent == null) {
            ExtentSparkReporter reporter = new ExtentSparkReporter("Report/ExtentReport.html");
            extent = new ExtentReports();
            extent.attachReporter(reporter);
        }
    }

    public static void flushReports() {
        if (extent != null) {
            extent.flush();
        }
    }
    public static void logPass(String message) {
        test.log(Status.PASS, message);
    }

    public static void logFail(String message) {
       test.log(Status.FAIL, message);
    }

    public static void logInfo(String message) {
        test.log(Status.INFO, message);
    }
    public static void createTest(String testName) {
        test = extent.createTest(testName);
    }

}
