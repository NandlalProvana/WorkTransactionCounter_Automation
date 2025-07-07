package cap.runner.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class IPACSLoginPage {
    private final WebDriver driver;

    // Element locators
    private final By partnerCodeField = By.id("partnerCode");
    private final By usernameField = By.xpath("//input[@placeholder='Username']");
    private final By passwordField = By.xpath("//input[@placeholder='Password']");
    private final By loginButton = By.id("Login");
    private final By dashboardText = By.xpath("//*[contains(text(),'View More IPACS Videos')]");

    // Error messages
    private final By invalidUserPassword = By.xpath("//span[contains(normalize-space(), 'Incorrect user name or password')]");
    private final By invalidPartnerCode = By.xpath("//span[contains(normalize-space(), 'Incorrect Partner Code')]");

    // Constructor
    public IPACSLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Explicit wait utility
    private void waitForVisibility(By locator, int timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Input Actions
    public void enterPartnerCode(String partnerCode) {
        waitForVisibility(partnerCodeField, 10);
        driver.findElement(partnerCodeField).sendKeys(partnerCode);
    }

    public void enterUsername(String username) {
        waitForVisibility(usernameField, 10);
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        waitForVisibility(passwordField, 10);
        driver.findElement(passwordField).sendKeys(password);
    }

    public void enterCredentials(String partnerCode, String username, String password) {
        enterPartnerCode(partnerCode);
        enterUsername(username);
        enterPassword(password);
    }

    // Button Clicks
    public void clickLogin() {
        waitForVisibility(loginButton, 20);
        driver.findElement(loginButton).click();
    }

    // Validations
    public boolean isDashboardVisible() {
        waitForVisibility(dashboardText, 15);
        return driver.findElements(dashboardText).size() > 0;
    }

    public boolean isWrongPartnerCode() {
        return isElementDisplayed(invalidPartnerCode, 10);
    }

    public boolean isWrongUserNamePassword() {
        return isElementDisplayed(invalidUserPassword, 10);
    }

    // Generic error check
    private boolean isElementDisplayed(By locator, int timeout) {
        try {
            waitForVisibility(locator, timeout);
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
