package cap.runner.stepdefinitions;

import cap.runner.page.IPACSHomePage;
import cap.runner.page.WTCHomePage;
import com.aventstack.extentreports.Status;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import utilities.BaseClass;
import utilities.ExtentReportManager;
import utilities.ScreenshotUtil;
import utilities.WindowHandler;

public class WTCLoginSteps extends BaseClass {

    IPACSHomePage ipacsHomePage;
    WTCHomePage wtcHomePage;

    @Then("the user clicks the Work Transaction Counter button")
    public void userClicksWTCButton() {
        ipacsHomePage = new IPACSHomePage(driver);
        ipacsHomePage.clickWorkTransactionCounter();

        logger.info("Clicked on Work Transaction Counter button");
        ExtentReportManager.getTest().log(Status.INFO, "Clicked on Work Transaction Counter button");
    }

    @Then("the WTC page should be displayed")
    public void verifyWTCPageDisplayed() {
        logger.info("Switching to new window");
        ExtentReportManager.getTest().log(Status.INFO, "Switching to new window");

        WindowHandler.switchToNewWindow(driver);

        boolean isDisplayed = ipacsHomePage.isNavigatedToWTCPage();
        if (isDisplayed) {
            logger.info("✅ WTC page loaded successfully.");
            ExtentReportManager.getTest().log(Status.PASS, "WTC page loaded successfully.");
        } else {
            logger.error("❌ WTC page did not load correctly.");
            ExtentReportManager.getTest().log(Status.FAIL, "WTC page did not load correctly.");
        }

        Assert.assertTrue(isDisplayed, "WTC page did not load correctly.");
    }

    @And("the user logs out of the application on WTC Page")
    public void theUserLogsOutOfTheApplicationOnWTCPage() {
        WTCHomePage wtcHomePage = new WTCHomePage(driver);
        try {
            logger.info("Attempting to log out of the WTC application...");

            wtcHomePage.clickLogoutButton();
            boolean isLogoutSuccessMessageDisplayed = wtcHomePage.isLogoutMessageVisible();

            Assert.assertTrue(isLogoutSuccessMessageDisplayed, "Logout success message is not displayed.");

            logger.info("Logout successful. Success message is visible.");
            ExtentReportManager.logPass("User successfully logged out of the WTC application.");
            ScreenshotUtil.attachToReport(driver, "Logout_Success");
        } catch (Exception e) {
            logger.error("An error occurred during logout: " + e.getMessage(), e);
            ExtentReportManager.logFail("Logout failed due to an exception: " + e.getMessage());

            Assert.fail("Logout failed: " + e.getMessage());
        }
    }
}

