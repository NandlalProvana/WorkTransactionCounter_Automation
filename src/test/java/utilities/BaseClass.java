package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class BaseClass {
    public static WebDriver driver;
    protected Logger logger;

    public BaseClass() {
        logger = LogManager.getLogger(this.getClass());
    }

    public void initializeDriver() {
        driver = DriverFactory.getDriver(); // Thread-safe
        try {
            driver.manage().window().maximize();
        } catch (Exception e) {
            logger.warn("⚠️ Could not maximize window: " + e.getMessage());
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        logger.info("✅ WebDriver initialized and browser launched.");
    }

    public void quitDriver() {
        DriverFactory.quitDriver();
        logger.info("✅ WebDriver quit and thread-local removed.");
    }
}
