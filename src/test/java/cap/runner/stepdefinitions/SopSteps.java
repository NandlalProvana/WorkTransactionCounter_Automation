package cap.runner.stepdefinitions;

import cap.runner.page.IPACSHomePage;
import cap.runner.page.IPACSLoginPage;
import cap.runner.page.IPACSSopPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import utilities.BaseClass;
import utilities.ExcelReaderFillo;
import utilities.ExtentReportManager;
import utilities.ScreenshotUtil;

public class SopSteps extends BaseClass {

    IPACSHomePage ipacsHomePage;
    IPACSSopPage sopPage;
    IPACSLoginPage loginPage;

    @Given("the user is logged into the IPACS application with environment {string}")
    public void the_user_is_logged_into_the_ipacs_application_with_environment(String env) {
        String url = ExcelReaderFillo.getEnvValue("URL", env);
        driver.get(url);

        loginPage = new IPACSLoginPage(driver);
        ipacsHomePage = new IPACSHomePage(driver);

        String partnerCode = ExcelReaderFillo.getEnvValue("PartnerCode", env);
        String username = ExcelReaderFillo.getEnvValue("LoginFromadmin", env);
        String password = ExcelReaderFillo.getEnvValue("Password", env);

        loginPage.enterCredentials(partnerCode, username, password);
        loginPage.clickLogin();

        boolean dashboardVisible = loginPage.isDashboardVisible();
        Assert.assertTrue(dashboardVisible, "Login failed: Dashboard not visible");

        logger.info("✅ Logged into IPACS with environment: " + env + " username: " + username);
        ExtentReportManager.logPass("✅ Logged into IPACS with environment: " + env + " username: " + username);
    }

    @And("the user navigates to the Add New SOP page")
    public void the_user_navigates_to_the_page() {
        sopPage = new IPACSSopPage(driver);


        sopPage.navigateToSOPCreation();
        logger.info("Navigated to Add New SOP page.");
        ExtentReportManager.logInfo("Navigated to Add New SOP page.");

    }

    @And("the user submits the SOP form")
    public void the_user_submits_the_sop_form() {
        sopPage.clickSubmit();
        logger.info("📤 Submitted the SOP successfully.");
        ExtentReportManager.logInfo("📤 Submitted the SOP successfully.");
    }

    @Then("the SOP should be created successfully")
    public void the_sop_should_be_created_successfully() throws InterruptedException {
        sopPage.clickOkPopup();
        Assert.assertTrue(sopPage.verifySuccessMessage("SOP Created Successfully"), "❌ SOP creation process failed.");
        logger.info("✅ SOP creation process completed.");
        ExtentReportManager.logPass("✅ SOP created successfully.");
        ScreenshotUtil.attachToReport(driver, "SOP_Creation_Success");
    }

    @When("the user fills in mandatory SOP profile details from {string} and selects accessibility option 1")
    public void theUserFillsInMandatorySOPProfileDetailsFromAndSelectsAccessibilityOption1(String env) throws InterruptedException {
        String sopOwner = ExcelReaderFillo.getEnvValue("SopOwner", env);
        String sopApprover = ExcelReaderFillo.getEnvValue("sopApprover", env);
        //String expirationDate = ExcelReaderFillo.getEnvValue("ExpirationDate", env);
        String expirationFrequency = ExcelReaderFillo.getEnvValue("ExpirationFrequency", env);
        String sopUploadDocumentName = ExcelReaderFillo.getEnvValue("uploadDocumentName", env);
        //String user = ExcelReaderFillo.getEnvValue("user", env);

        String sopName = "Automation SOP - " + System.currentTimeMillis();
        sopPage.enterSOPName(sopName);


        ExcelReaderFillo.setEnvValue("LatestSOPName", env, sopName);

        sopPage.selectSopOwner(sopOwner);
        sopPage.enterDescription("This is an auto-generated SOP for testing purposes.");
        sopPage.selectApprover(sopApprover);
        sopPage.selectDateFromZebraDatePicker("17", "July", "2025");
        sopPage.enterExpirationFrequency(expirationFrequency);

        sopPage.selectAccessibilityOption(1);

        logger.info("✔ Accessibility Option 1 selected.");
        ExtentReportManager.logInfo("✔ Accessibility Option 1 selected.");

        sopPage.clickSaveAndNext();
        sopPage.clickOkPopup();


        sopPage.uploadDocument(sopUploadDocumentName);
        Assert.assertTrue(sopPage.verifyUploadedFile(sopUploadDocumentName), "Waiting for document available after upload");

        sopPage.clickSaveAndNext();
        sopPage.clickOkUntilApproveVisible(10);


        sopPage.clickApprove();
        sopPage.clickOkPopup();
        sopPage.clickSkipIfPresent();
        //sopPage.selectUsers(user);

        logger.info("Filled mandatory SOP fields from environment: " + env);
        ExtentReportManager.logInfo("Filled mandatory SOP fields from environment: " + env);
    }

    @When("the user fills in mandatory SOP profile details from {string} and selects accessibility option 2")
    public void theUserFillsInMandatorySOPProfileDetailsFromAndSelectsAccessibilityOption2(String env) throws InterruptedException {
        String sopOwner = ExcelReaderFillo.getEnvValue("SopOwner", env);
        String sopApprover = ExcelReaderFillo.getEnvValue("sopApprover", env);
        String expirationDate = ExcelReaderFillo.getEnvValue("ExpirationDate", env);
        String expirationFrequency = ExcelReaderFillo.getEnvValue("enterExpirationFrequency", env);
        String sopUploadDocumentName = ExcelReaderFillo.getEnvValue("uploadDocument", env);
        String user = ExcelReaderFillo.getEnvValue("user", env);
        String sopName = "Automation SOP - " + System.currentTimeMillis();
        sopPage.enterSOPName(sopName);


        ExcelReaderFillo.setEnvValue("LatestSOPName", env, sopName);
        sopPage.selectSopOwner(sopOwner);
        sopPage.enterDescription("This is an auto-generated SOP for testing purposes.");
        sopPage.selectApprover(sopApprover);
        sopPage.selectDateFromZebraDatePicker("17", "July", "2025");
        sopPage.enterExpirationFrequency(expirationFrequency);
        sopPage.selectAccessibilityOption(2);
        logger.info("✔ Accessibility Option 2 selected.");
        ExtentReportManager.logInfo("✔ Accessibility Option 2 selected.");

        sopPage.clickSaveAndNext();
        sopPage.clickOkPopup();

        sopPage.uploadDocument(sopUploadDocumentName);

        sopPage.verifyUploadedFile(sopUploadDocumentName);

        sopPage.clickOkPopup();

        sopPage.clickApprove();
        sopPage.clickOkPopup();
        sopPage.clickSkipIfPresent();
        sopPage.selectUsers(user);

        logger.info("Filled mandatory SOP fields from environment: " + env);
        ExtentReportManager.logInfo("Filled mandatory SOP fields from environment: " + env);
    }


    @When("the user fills in mandatory SOP profile details from {string} and selects accessibility option 3")
    public void theUserFillsInMandatorySOPProfileDetailsFromAndSelectsAccessibilityOption(String env) throws InterruptedException {
        String sopOwner = ExcelReaderFillo.getEnvValue("SopOwner", env);
        String sopApprover = ExcelReaderFillo.getEnvValue("sopApprover", env);
        String expirationDate = ExcelReaderFillo.getEnvValue("ExpirationDate", env);
        String expirationFrequency = ExcelReaderFillo.getEnvValue("enterExpirationFrequency", env);
        String sopUploadDocumentName = ExcelReaderFillo.getEnvValue("uploadDocument", env);
        String user = ExcelReaderFillo.getEnvValue("user1", env);

        sopPage.enterSOPName("Automation SOP - " + System.currentTimeMillis());
        sopPage.selectSopOwner(sopOwner);
        sopPage.enterDescription("This is an auto-generated SOP for testing purposes.");
        sopPage.selectApprover(sopApprover);
        sopPage.selectDateFromZebraDatePicker("17", "July", "2025");
        sopPage.enterExpirationFrequency(expirationFrequency);
        sopPage.selectAccessibilityOption(3);
        logger.info("✔ Accessibility Option 3 selected.");
        ExtentReportManager.logInfo("✔ Accessibility Option 3 selected.");

        sopPage.clickSaveAndNext();
        sopPage.clickOkPopup();

        Assert.assertTrue(sopPage.verifyUploadedFile(sopUploadDocumentName), "❌ Document not uploaded or not found in UI: " + sopUploadDocumentName);
        sopPage.clickApprove();
        sopPage.clickOkPopup();
        sopPage.clickSkipIfPresent();
        sopPage.selectUsers(user);
        sopPage.selectUsers(sopOwner);

        logger.info("Filled mandatory SOP fields from environment: " + env);
        ExtentReportManager.logInfo("Filled mandatory SOP fields from environment: " + env);
    }


    @When("the user searches for the created SOP in the search field from {string}")
    public void theUserSearchesForTheCreatedSOPInTheSearchFieldFrom(String env) {
        String latestSOPName = ExcelReaderFillo.getEnvValue("LatestSOPName", env);
        IPACSSopPage sopPage = new IPACSSopPage(driver);
        sopPage.enterSopAndSearch(latestSOPName);
    }


    @Then("the SOP should be visible in the grid from {string}")
    public void theSOPShouldBeVisibleInTheGridFrom(String env) {
        IPACSSopPage sopPage = new IPACSSopPage(driver);
        String latestSOPName = ExcelReaderFillo.getEnvValue("LatestSOPName", env);
        Assert.assertTrue(sopPage.isSopPresentInTheGrid(latestSOPName), "❌ SOP not found in grid: " + latestSOPName);
        ScreenshotUtil.attachToReport(driver, "The Sop is visible in the grid");
    }
}
