package cap.runner.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class IPACSHomePage {
    WebDriver driver;

    // Locators
    private final By wtcModule = By.xpath("//a[normalize-space()='Work Transaction Counter']");
    private final By logoutButton = By.xpath("//a[normalize-space()='Log Out']");
    private final By homeLink = By.xpath("//a[normalize-space()='Home']");
    private final By profileIcon = By.xpath("//div[@id='showprofilePopup']/img");
    private final By skipButton = By.xpath("//button[normalize-space()='Skip']");

    // Constructor
    public IPACSHomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Generic explicit wait method
    public void waitForElement(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Actions
    public void clickSkipButton1() {
        waitForElement(skipButton, 10);
        driver.findElement(skipButton).click();
    }

    public void clickSkipButton() {
        try {
            if (driver.findElements(skipButton).size() > 0 && driver.findElement(skipButton).isEnabled()) {
                driver.findElement(skipButton).click();
            }
        } catch (Exception e) {
            // Log or silently ignore if needed
            System.out.println("Skip button not present or not clickable.");
        }
    }


    public void hoverProfile() {
        waitForElement(profileIcon, 15);
        driver.findElement(profileIcon).click();
    }

    public void clickLogoutButton() {
        waitForElement(logoutButton, 10);
        driver.findElement(logoutButton).click();
    }

    public void clickWorkTransactionCounter() {
        waitForElement(wtcModule, 10);
        driver.findElement(wtcModule).click();
    }

    public boolean isNavigatedToIPACSPage() {
        String expectedTitle = "Provana IPACS – IPACS";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleContains(expectedTitle));

        String actualTitle = driver.getTitle();
        return actualTitle.equalsIgnoreCase(expectedTitle);
    }

    public boolean isNavigatedToWTCPage() {
        String expectedTitle = "WTCWEB web";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleContains(expectedTitle));

        String actualTitle = driver.getTitle();
        return actualTitle.equalsIgnoreCase(expectedTitle);
    }
}
