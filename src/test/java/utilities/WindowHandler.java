package utilities;

import org.openqa.selenium.WebDriver;

import java.util.Set;

public class WindowHandler {

    // Switch to a new window that is not the current one
    public static void switchToNewWindow(WebDriver driver) {
        String currentWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            if (!window.equals(currentWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
    }

    // Switch to a specific window using title
    public static void switchToWindowByTitle(WebDriver driver, String expectedTitle) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            driver.switchTo().window(window);
            if (driver.getTitle().contains(expectedTitle)) {
                break;
            }
        }
    }

    // Switch back to the main/original window
    public static void switchToMainWindow(WebDriver driver, String mainWindowHandle) {
        driver.switchTo().window(mainWindowHandle);
    }
}
