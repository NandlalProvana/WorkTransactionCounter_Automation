package cap.runner.page;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ExtentReportManager;
import utilities.ScreenshotUtil;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

public class IPACSSopPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    public IPACSSopPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.actions = new Actions(driver);
    }

    // --------------------- Locators ---------------------
    private final By policiesMenu = By.xpath("//a[normalize-space()='Policies and Procedures']");
    private final By sopsMenu = By.xpath("//a[normalize-space()='SOPs']");
    private final By addNewSOPButton = By.xpath("//a[normalize-space()='Add New SOP']");
    private final By sopNameField = By.id("txtSopName");
    private final By descriptionField = By.id("txtDescription");
    private final By sopOwnerDropdown = By.xpath("//div[@id='ddlSopOwner_chzn']//a");
    private final By expirationDateField = By.id("txtExpirationDate");
    private final By frequencyField = By.id("txtFrequency");

    private final By saveAndNextButton = By.xpath("//button[normalize-space()='Save & Next']");
    private final By okPopupButton = By.id("popup_ok");
    private final By submitButton = By.xpath("//button[@type='Submit']");
    private final By approveButton = By.xpath("//div[@class='ND_PnPbtn ND_PnPApproveDocumentEvent']");
    private final By skipButton = By.xpath("//a[normalize-space()='Skip']");

    private final By documentUploadInput = By.id("ND_UploadDocument");
    private final By uploadedDocDiv = By.xpath("//div[@id='ND_uploadedFiles']//div[contains(@class,'ND_PnPUploadedDocNameDiv')]");
    private final By popupMessage = By.id("popup_message");

    private final By radio1 = By.id("sopCategoryID_1");
    private final By radio2 = By.id("sopCategoryID_2");
    private final By radio3 = By.id("sopCategoryID_3");

    private final By search = By.xpath("//input[@placeholder='Search']");

    // --------------------- Navigation ---------------------

    public void navigateToSOPCreation() {
        click(policiesMenu, "Policies and Procedures menu");
        click(sopsMenu, "SOPs submenu");
        click(addNewSOPButton, "Add New SOP button");
    }

    // --------------------- Form Input ---------------------

    public void enterSOPName(String sopName) {
        sendKeys(sopNameField, sopName, "SOP Name");
    }

    public void enterDescription(String description) {
        sendKeys(descriptionField, description, "Description");
    }

    public void enterExpirationFrequency(String frequency) {
        sendKeys(frequencyField, frequency, "Expiration Frequency");
    }

    public void enterSopAndSearch(String value) {
        sendKeys(search, value, "Enter the value in search Field");
    }

    public void selectAccessibilityOption(int option) {
        By radioLocator;
        switch (option) {
            case 1:
                radioLocator = radio1;
                break;
            case 2:
                radioLocator = radio2;
                break;
            case 3:
                radioLocator = radio3;
                break;
            default:
                throw new IllegalArgumentException("Invalid accessibility option: " + option);
        }
        click(radioLocator, "Accessibility Radio Option " + option);
    }

    // --------------------- Buttons ---------------------

    public void clickSaveAndNext() throws InterruptedException {
        click(saveAndNextButton, "Save & Next button");

    }

    public void clickOkPopup() throws InterruptedException {

        click(okPopupButton, "OK Popup button");
    }

    public void clickOkUntilApproveVisible(int maxAttempts) {
        int attempts = 0;

        while (attempts < maxAttempts) {
            try {
                // If Approve button is visible, break the loop
                if (driver.findElements(approveButton).size() > 0 &&
                        driver.findElement(approveButton).isDisplayed()) {
                    ExtentReportManager.logInfo("✅ Approve button is visible. No more OK popups to click.");
                    break;
                }


                // If OK popup is visible, click it
                WebElement okBtn = wait.withTimeout(Duration.ofSeconds(2))
                        .until(ExpectedConditions.visibilityOfElementLocated(okPopupButton));

                if (okBtn.isDisplayed()) {
                    okBtn.click();
                    clickSaveAndNext();
                    ExtentReportManager.logInfo("Clicked OK popup (Attempt " + (attempts + 1) + ")");
                    ScreenshotUtil.attachToReport(driver, "OK_Click_Attempt_" + attempts);
                    Thread.sleep(2000); // small delay before next check
                }
            } catch (Exception e) {
                // OK popup may not be present, so we skip
                ExtentReportManager.logInfo("No OK popup found during attempt " + (attempts + 1));
            }

            attempts++;
        }
    }


    public void clickSubmit() {
        click(submitButton, "Submit button");
    }

    public void clickApprove() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
        wait.until(ExpectedConditions.visibilityOfElementLocated(approveButton));
        click(approveButton, "Approve button");
    }

    public void clickSkipIfPresent() {
        try {
            WebElement skip = wait.until(ExpectedConditions.visibilityOfElementLocated(skipButton));
            skip.click();
            ExtentReportManager.logInfo("Clicked Skip button");
        } catch (TimeoutException ignored) {
            ExtentReportManager.logInfo("Skip button not present");
        }
    }

    // --------------------- Uploads ---------------------

    public void uploadDocument(String fileName) {
        try {
            String filePath = Paths.get(System.getProperty("user.dir"), "src", "test", "resources", "testdata", fileName).toString();
            WebElement uploadInput = wait.until(ExpectedConditions.presenceOfElementLocated(documentUploadInput));
            uploadInput.sendKeys(filePath);
            ExtentReportManager.logInfo("📎 Uploaded file: " + fileName);
        } catch (Exception e) {
            handleError("❌ File upload failed", e);
        }
    }

    public boolean verifyUploadedFile(String expectedFileName) {
        try {
            WebElement uploaded = wait.until(ExpectedConditions.visibilityOfElementLocated(uploadedDocDiv));
            String actual = uploaded.getText().trim();
            boolean matched = actual.equalsIgnoreCase(expectedFileName);
            ExtentReportManager.logInfo("Uploaded file found: " + actual);
            return matched;
        } catch (Exception e) {
            ExtentReportManager.logFail("❌ Uploaded file verification failed");
            ScreenshotUtil.attachToReport(driver, "UploadedFileVerificationFailed");
            return false;
        }
    }

    public boolean verifySuccessMessage(String expectedText) {
        try {
            WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(popupMessage));
            String actual = message.getText().trim();
            ExtentReportManager.logInfo("Popup message: " + actual);
            return actual.equalsIgnoreCase(expectedText);
        } catch (Exception e) {
            ExtentReportManager.logFail("❌ Success message not found or mismatched");
            ScreenshotUtil.attachToReport(driver, "SuccessMessageMismatch");
            return false;
        }
    }

    // --------------------- Helper Methods ---------------------

    public boolean isSopPresentInTheGrid(String sopName) {
        if (sopName == null || sopName.trim().isEmpty()) {
            ExtentReportManager.logFail("❌ SOP name is null or empty");
            return false;
        }

        try {
            String dynamicXPath = String.format("//span[normalize-space()='%s']", sopName);
            By sopLocator = By.xpath(dynamicXPath);
            WebElement sopElement = wait.until(ExpectedConditions.visibilityOfElementLocated(sopLocator));

            String actualSop = sopElement.getText().trim();

            if (sopName.equalsIgnoreCase(actualSop)) {
                ExtentReportManager.logInfo("✅ SOP found in grid: " + actualSop);
                return true;
            } else {
                ExtentReportManager.logFail("❌ SOP text mismatch. Expected: " + sopName + ", Found: " + actualSop);
                return false;
            }

        } catch (Exception e) {
            ExtentReportManager.logFail("❌ SOP not found in grid: " + sopName + " - " + e.getMessage());
            ScreenshotUtil.attachToReport(driver, "SOP_Not_Found_" + sopName.replaceAll("\\s+", "_"));
            return false;
        }
    }


    private void click(By locator, String elementName) {
        try {
            WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
            el.click();
            ExtentReportManager.logInfo("✅ Clicked: " + elementName);
        } catch (Exception e) {
            handleError("❌ Click failed on: " + elementName, e);
        }
    }

    private void sendKeys(By locator, String value, String fieldName) {
        try {
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            field.clear();
            field.sendKeys(value);
            ExtentReportManager.logInfo("✍️ Entered in " + fieldName + ": " + value);
        } catch (Exception e) {
            handleError("❌ Failed to enter value in " + fieldName, e);
        }
    }

    private void handleError(String message, Exception e) {
        ExtentReportManager.logFail(message + " | " + e.getMessage());
        ScreenshotUtil.attachToReport(driver, message.replaceAll(" ", "_"));
        throw new RuntimeException(message, e);
    }

    public void selectSopOwner(String owner) {
        try {
            By dropdown = By.xpath("//div[@id='ddlSopOwner_chzn']//a");
            By searchBox = By.xpath("//div[@id='ddlSopOwner_chzn']//input");
            waitAndClick(dropdown, "SOP Owner dropdown");
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
            input.sendKeys(owner);
            input.sendKeys(Keys.ENTER);
            ExtentReportManager.logInfo("Selected SOP Owner: " + owner);
        } catch (Exception e) {
            handleError("Failed to select SOP Owner", e);
        }
    }


    public void selectApprover(String value) {

        selectFromChosenDropdown("ddlApprover", value);
    }

    public void selectFromChosenDropdown(String dropdownId, String optionText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Click the dropdown toggle (e.g., #ddlSopOwner_chzn > a)
        By dropdownToggle = By.xpath("//div[@id='" + dropdownId + "_chzn']");
        WebElement toggle = wait.until(ExpectedConditions.elementToBeClickable(dropdownToggle));
        toggle.click();

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", toggle);

        // Wait for the search input and type the option text
        By searchInput = By.xpath("//div[@id='" + dropdownId + "_chzn']//input[@type='text']");
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", input);
        input.clear();
        input.sendKeys(optionText);

        // Press Enter to select the highlighted option
        input.sendKeys(Keys.ENTER);
    }


    public void selectUsers(String username) {
        try {
            By dropdown = By.xpath("//div[@id='ddlUsers_chzn']//a");
            By searchBox = By.xpath("//div[@id='ddlUsers_chzn']//input");

            waitAndClick(dropdown, "User selection dropdown");
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
            input.sendKeys(username);
            input.sendKeys(Keys.ENTER);

            ExtentReportManager.logInfo("Selected User: " + username);
        } catch (Exception e) {
            handleError("Failed to select user", e);
        }
    }

    private void waitAndClick(By locator, String elementName) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

            // Scroll into view if needed
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

            // Click using Actions for stability
            actions.moveToElement(element).click().perform();

            ExtentReportManager.logInfo("✔ Clicked: " + elementName);
        } catch (Exception e) {
            handleError("❌ Failed to click: " + elementName, e);
        }
    }


    public void selectDateFromZebraDatePicker(String expectedDay, String expectedMonth, String expectedYear) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement calendarIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.Zebra_DatePicker_Icon")));
            calendarIcon.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.Zebra_DatePicker")));

            expectedMonth = expectedMonth.trim().toLowerCase();
            expectedYear = expectedYear.trim();

            int attempts = 0;
            boolean found = false;

            while (attempts++ < 24) {
                WebElement caption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("td.dp_caption")));
                String captionText = caption.getText().trim().replace(",", "").toLowerCase(); // "july 2025"
                String[] parts = captionText.split("\\s+");

                if (parts.length >= 2) {
                    String actualMonth = parts[0];
                    String actualYear = parts[1];

                    if (actualMonth.equals(expectedMonth) && actualYear.equals(expectedYear)) {
                        found = true;
                        break;
                    }
                }

                // Go to next month
                WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("td.dp_next")));
                nextBtn.click();
                Thread.sleep(300); // brief wait for UI update
            }

            if (!found) {
                throw new RuntimeException("❌ Could not navigate to expected month/year: " + expectedMonth + " " + expectedYear);
            }

            // Now select the day
            List<WebElement> dayCells = driver.findElements(By.xpath(
                    "//table[@class='dp_daypicker']//td[not(contains(@class, 'dp_not_in_month')) and normalize-space(text())='" + expectedDay + "']"
            ));

            if (!dayCells.isEmpty()) {
                dayCells.get(0).click();
                //logger.info("✅ Selected date: " + expectedDay + " " + expectedMonth + " " + expectedYear);
                ExtentReportManager.logInfo("✅ Selected date: " + expectedDay + " " + expectedMonth + " " + expectedYear);
            } else {
                throw new NoSuchElementException("❌ Day " + expectedDay + " not found in current calendar view.");
            }

        } catch (Exception e) {
            ScreenshotUtil.attachToReport(driver, "DatePickerError");
            //logger.error("❌ Date selection error: " + e.getMessage(), e);
            ExtentReportManager.logFail("❌ Date selection error: " + e.getMessage());
            throw new RuntimeException("❌ Failed to select date", e);
        }
    }


}
