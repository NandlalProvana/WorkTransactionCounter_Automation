package cap.runner.stepdefinitions;

import cap.runner.page.IPACSHomePage;
import cap.runner.page.IPACSLoginPage;
import cap.runner.page.WTCHomePage;
import com.aventstack.extentreports.Status;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import utilities.*;

public class LoginSteps extends BaseClass {
    IPACSLoginPage loginPage;
    WTCHomePage wtcHomePage;
    IPACSHomePage ipacsHomePage;


    @Given("the user is on the IPACS login page from {string}")
    public void user_is_on_login_page_from_env(String env) {
        String url = ExcelReaderFillo.getEnvValue("URL", env);
        driver.get(url);
        loginPage = new IPACSLoginPage(driver); // ✅ Ensure page is initialized
        logger.info("User navigated to URL: " + url);
        ExtentReportManager.getTest().log(Status.INFO, "Navigated to IPACS URL: " + url);
    }

    @When("the user enters credentials from {string}")
    public void user_enters_credentials_from_env(String env) {
        String partnerCode = ExcelReaderFillo.getEnvValue("PartnerCode", env);
        String username = ExcelReaderFillo.getEnvValue("UserName", env);
        String password = ExcelReaderFillo.getEnvValue("Password", env);

        loginPage.enterCredentials(partnerCode, username, password);
        logger.info("Entered credentials from Excel: PartnerCode=" + partnerCode + ", Username=" + username);
        ExtentReportManager.getTest().log(Status.INFO, "Entered credentials from Excel for env: " + env);
    }

    @When("the user clicks the login button")
    public void user_clicks_login() {
        loginPage.clickLogin();
        logger.info("Clicked Login button");
        ExtentReportManager.getTest().log(Status.INFO, "Clicked login button");
    }

    @Then("the user should be redirected to the dashboardPage")
    public void user_redirected_to_dashboard() {
        boolean isVisible = loginPage.isDashboardVisible();
        Assert.assertTrue(isVisible, "Dashboard was not visible.");
        logger.info("Dashboard visibility confirmed: " + isVisible);
        ExtentReportManager.getTest().log(Status.PASS, "User redirected to dashboard page successfully");
        ScreenshotUtil.attachToReport(driver, "DashboardLoaded");
    }


    @Then("the user should see an invalid login message")
    public void theUserShouldSeeAnInvalidLoginMessage() {
        boolean isVisible = loginPage.isWrongUserNamePassword(); // ✅ Correct method

        Assert.assertTrue(isVisible, "❌ Invalid login message not displayed as expected.");
        logger.info("✅ Invalid login message visibility confirmed: " + isVisible);
        ExtentReportManager.getTest().log(Status.PASS, "❗ Invalid login message appeared as expected");
        ScreenshotUtil.attachToReport(driver, "Invalid_Login_Attempt");
    }


    @When("the user enters credentials from {string} using InvalidPassword")
    public void theUserEntersCredentialsFromUsingInvalidPassword(String env) {
        String partnerCode = ExcelReaderFillo.getEnvValue("PartnerCode", env);
        String username = ExcelReaderFillo.getEnvValue("UserName", env);
        String Invalidpassword = ExcelReaderFillo.getEnvValue("InvalidPassword", env);

        loginPage.enterCredentials(partnerCode, username, Invalidpassword);
        logger.info("Entered credentials from Excel: PartnerCode=" + partnerCode + ", Username=" + username);
        ExtentReportManager.getTest().log(Status.INFO, "Entered credentials from Excel for env: " + env);
    }

    @When("the user enters credentials from {string} using InvalidUserName")
    public void theUserEntersCredentialsFromUsingInvalidUserName(String env) {
        String partnerCode = ExcelReaderFillo.getEnvValue("PartnerCode", env);
        String Invalidusername = ExcelReaderFillo.getEnvValue("InvalidUserName", env);
        String password = ExcelReaderFillo.getEnvValue("Password", env);

        loginPage.enterCredentials(partnerCode, Invalidusername, password);
        logger.info("Entered credentials from Excel: PartnerCode=" + partnerCode + ", Username=" + Invalidusername);
        ExtentReportManager.getTest().log(Status.INFO, "Entered credentials from Excel for env: " + env);
    }

    @When("the user enters credentials from {string} using InvalidPartnerCode")
    public void theUserEntersCredentialsFromUsingInvalidPartnerCode(String env) {
        String InvalidpartnerCode = ExcelReaderFillo.getEnvValue("InvalidPartnerCode", env);
        try {
            String username = ExcelReaderFillo.getEnvValue("UserName", env);

            String password = ExcelReaderFillo.getEnvValue("Password", env);

            loginPage.enterCredentials(InvalidpartnerCode, username, password);
        } catch (Exception e) {
            System.out.println("User is unable to enter username and password:" + e);
        }
        logger.info("Entered credentials from Excel: PartnerCode=" + InvalidpartnerCode);
        ExtentReportManager.getTest().log(Status.INFO, "Entered credentials from Excel for env: " + env);
    }


    @Then("the user should see an invalid partner code message")
    public void theUserShouldSeeAnInvalidInvalidPartnerCodeMessage() {
        boolean isVisible = loginPage.isWrongPartnerCode(); // ✅ You may rename this to isLoginErrorVisible() for clarity

        Assert.assertTrue(isVisible, "❌ Invalid login message not displayed as expected.");

        logger.info("✅ Invalid login message visibility confirmed: " + isVisible);
        ExtentReportManager.getTest().log(Status.PASS, "❗ Invalid login message appeared as expected");

        ScreenshotUtil.attachToReport(driver, "Invalid_Login_Attempt");
    }

    @Then("the user should be on the WTC page")
    public void theUserShouldBeOnTheWTCPage() {
        logger.info("Switching to new window");
        ExtentReportManager.getTest().log(Status.INFO, "Switching to new window");

        wtcHomePage = new WTCHomePage(driver);
        WindowHandler.switchToNewWindow(driver);

        try {
            Assert.assertTrue(wtcHomePage.isWTCHeaderVisible(), "❌ User is not on the WTC Page.");
            logger.info("✅ User is on WTC page");
            ExtentReportManager.logPass("✅ User is on WTC page");
            ScreenshotUtil.attachToReport(driver, "WTC_Page");
        } catch (Exception e) {
            logger.error("❌ WTC Page element not found or not visible.", e);
            Assert.fail("WTC Page element validation failed: " + e.getMessage());
        }
    }

    @When("the user clicks the back button on the Work Transaction Counter page")
    public void theUserClicksTheBackButtonOnTheWorkTransactionCounterPage() {
        try {
            Assert.assertTrue(wtcHomePage.isBackButtonVisible(), "❌ Back button not visible.");
            wtcHomePage.clickBackToIPACSButton();
            logger.info("🔙 User clicked on Back to IPACS button");
            ExtentReportManager.logInfo("🔙 User clicked on Back to IPACS button");
        } catch (Exception e) {
            logger.error("❌ Failed to click back button on WTC page", e);
            Assert.fail("Back button interaction failed: " + e.getMessage());
        }
    }

    @Then("the user should be redirected back to the IPACS dashboard")
    public void theUserShouldBeRedirectedBackToTheIPACSDashboard() {
        WindowHandler.switchToWindowByTitle(driver, "Provana IPACS – IPACS");
        logger.info("Switching to Back Provana");
        ExtentReportManager.getTest().log(Status.INFO, "Switching to Back Provana");
        ipacsHomePage = new IPACSHomePage(driver);
        // WindowHandler.switchToWindowByTitle(driver, "Provana IPACS – IPACS");
        try {
            Assert.assertTrue(ipacsHomePage.isNavigatedToIPACSPage(), "❌ User is not on the IPACS dashboard.");
            logger.info("✅ User is redirected back to the IPACS dashboard");
            ExtentReportManager.logPass("✅ User is redirected back to the IPACS dashboard");
            ScreenshotUtil.attachToReport(driver, "Back_Ipacs_Page");
        } catch (Exception e) {
            logger.error("❌ Failed to verify redirection to IPACS dashboard", e);
            Assert.fail("Dashboard redirection validation failed: " + e.getMessage());
        }
    }

    @And("the user logs out of the application on Ipacs Page")
    public void theUserLogsOutOfTheApplicationOnIpacsPage() {
        IPACSHomePage ipacsHomePage = new IPACSHomePage(driver);
        try {

            ipacsHomePage.hoverProfile();

            ipacsHomePage.clickLogoutButton();

            ipacsHomePage.clickSkipButton();
            boolean isLoginPageVisible = loginPage.isDashboardVisible();
            Assert.assertTrue(isLoginPageVisible, "❌ Logout failed: Login page not visible.");
            logger.info("🔒 User successfully logged out and login page is visible.");
            ExtentReportManager.logPass("🔒 Logout successful and login page appeared.");
            ScreenshotUtil.attachToReport(driver, "Logout_Success");

        } catch (Exception e) {
            logger.error("❌ Logout step failed: " + e.getMessage(), e);
            ExtentReportManager.logFail("❌ Logout step failed.");
            //ScreenshotUtil.attachToReport(driver, "Logout_Failure");
            throw e;
        }

    }

    @When("the user enters credentials for WTCProcessExpert from {string}")
    public void theUserEntersCredentialsForWTCProcessExpertFrom(String env) {
        String partnerCode = ExcelReaderFillo.getEnvValue("PartnerCode", env);
        String username = ExcelReaderFillo.getEnvValue("UserWTCProcessExpert", env);
        String password = ExcelReaderFillo.getEnvValue("Password", env);

        loginPage.enterCredentials(partnerCode, username, password);
        logger.info("Entered credentials from Excel: PartnerCode=" + partnerCode + ", Username=" + username);
        ExtentReportManager.getTest().log(Status.INFO, "Entered credentials from Excel for env: " + env + " Username= " + username);

    }

    @When("the user enters credentials for WTCProcessOwner from {string}")
    public void theUserEntersCredentialsForWTCProcessOwnerFrom(String env) {
        String partnerCode = ExcelReaderFillo.getEnvValue("PartnerCode", env);
        String username = ExcelReaderFillo.getEnvValue("UserWTCProcessOwner", env);
        String password = ExcelReaderFillo.getEnvValue("Password", env);

        loginPage.enterCredentials(partnerCode, username, password);
        logger.info("Entered credentials from Excel: PartnerCode=" + partnerCode + ", Username=" + username);
        ExtentReportManager.getTest().log(Status.INFO, "Entered credentials from Excel for env: " + env + " Username= " + username);
    }
}
