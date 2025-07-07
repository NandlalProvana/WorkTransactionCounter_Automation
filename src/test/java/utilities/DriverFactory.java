package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver == null) {
            currentDriver = new ChromeDriver();
            driver.set(currentDriver);
        }
        return currentDriver;
    }

    public static void setDriver(WebDriver newDriver) {
        driver.set(newDriver);
    }

    public static void quitDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            try {
                currentDriver.quit();
            } catch (Exception e) {
                e.printStackTrace(); // Optional: log error
            } finally {
                driver.remove(); // Clean up thread-local memory
            }
        }
    }
}
