package cap.runner.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WTCHomePage {
    private final WebDriver driver;

    private final By wtcHeader = By.xpath("//span[normalize-space()='Work Transaction Counter']");
    private final By sopElement = By.xpath("//span[normalize-space()='Master List of SOPs']");
    private final By logoutElement = By.xpath("//button[@aria-label='Logout']//i//img");
    private final By backButtonElement = By.xpath("//a[normalize-space()='Back to IPACS']");
    private final By logoutSuccessMessage = By.xpath("//h3[normalize-space()='You have been logged out successfully']");

    public WTCHomePage(WebDriver driver) {
        this.driver = driver;
    }

    // ========== Utility Methods ==========

    private void waitForVisibility(By locator, int timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private boolean isElementVisible(By locator, int timeoutInSeconds) {
        try {
            waitForVisibility(locator, timeoutInSeconds);
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // ========== Page Actions ==========

    public void clickLogoutButton() {
        waitForVisibility(logoutElement, 10);
        driver.findElement(logoutElement).click();
    }

    public void clickBackToIPACSButton() {
        waitForVisibility(backButtonElement, 10);
        driver.findElement(backButtonElement).click();
    }

    // ========== Validations ==========

    public boolean isLogoutMessageVisible() {
        return isElementVisible(logoutSuccessMessage, 15);
    }

    public boolean isBackButtonVisible() {
        return isElementVisible(backButtonElement, 10);
    }

    public boolean isWTCHeaderVisible() {
        return isElementVisible(wtcHeader, 10);
    }

    public boolean isSOPSectionVisible() {
        return isElementVisible(sopElement, 10);
    }

    public boolean isIPACSDashboardVisible() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        return driver.getTitle().contains("IPACS") ||
                driver.getPageSource().contains("View More IPACS Videos");
    }
}
