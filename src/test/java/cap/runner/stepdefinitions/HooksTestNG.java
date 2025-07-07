package cap.runner.stepdefinitions;

import org.testng.annotations.AfterSuite;
import utilities.ExtentReportManager;

public class HooksTestNG {
    @AfterSuite
    public void afterSuiteFlushReport() {
        System.out.println("📄 Flushing Extent Report after all scenarios...");
        ExtentReportManager.flushReport();
    }
}
