package cap.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import utilities.ExtentReportManager;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "cap.runner.stepdefinitions",
        tags = "@run", // 👈 Only this scenario will run
        plugin = {"pretty", "html:target/cucumber-report.html"},
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @AfterSuite
    public void tearDownSuite() {
        ExtentReportManager.flushReport();  // 🔁 Flush once after all features
        System.out.println("✅ ExtentReport flushed after all feature executions.");
    }

}
