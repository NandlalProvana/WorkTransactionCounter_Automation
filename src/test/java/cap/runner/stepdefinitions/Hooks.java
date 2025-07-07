package cap.runner.stepdefinitions;

import com.aventstack.extentreports.MediaEntityBuilder;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utilities.BaseClass;
import utilities.ExtentReportManager;
import utilities.ScreenshotUtil;

public class Hooks extends BaseClass {

    private long scenarioStartTime;

    @Before
    public void setUpScenario(Scenario scenario) {
        try {
            initializeDriver();
            ExtentReportManager.getInstance();

            ExtentReportManager.createScenario("🧪 " + scenario.getName());

            scenarioStartTime = System.currentTimeMillis(); // ⏱️ Capture start time

            ExtentReportManager.logInfo("⚙️ Test setup started for: " + scenario.getName());
            logger.info("🚀 Started scenario: " + scenario.getName());

        } catch (Exception e) {
            logger.error("❌ Setup failed: " + e.getMessage(), e);
            throw e;
        }
    }

    @After
    public void tearDownScenario(Scenario scenario) {
        String scenarioName = scenario.getName();
        long duration = System.currentTimeMillis() - scenarioStartTime;

        try {
            if (driver != null && scenario.isFailed()) {
                String screenshotPath = ScreenshotUtil.capture(driver, scenarioName);
                if (screenshotPath != null && !screenshotPath.isEmpty()) {
                    ExtentReportManager.getTest().fail("❌ Screenshot on failure:",
                            MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                    ExtentReportManager.logInfo("📸 Screenshot attached for failure: " + scenarioName);
                }
            }

            if (scenario.isFailed()) {
                ExtentReportManager.logFail("❌ Scenario failed: " + scenarioName);
                logger.error("Scenario failed: " + scenarioName);
            } else {
                ExtentReportManager.logPass("✅ Scenario passed: " + scenarioName);
                logger.info("Scenario passed: " + scenarioName);
            }

            // ⏱️ Log scenario duration
            ExtentReportManager.logScenarioTime(duration);

        } catch (Exception e) {
            logger.error("⚠️ Error in @After for scenario: " + scenarioName + " - " + e.getMessage(), e);
        } finally {
            quitDriver();
            logger.info("🧹 WebDriver closed for scenario: " + scenarioName);
        }
    }
}
