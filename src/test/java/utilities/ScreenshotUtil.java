package utilities;

import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String capture(WebDriver driver, String name) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = name.replaceAll("\\s+", "_") + "_" + timestamp + ".png";
            String relativePath = "test-output/screenshots/" + fileName;
            String absolutePath = System.getProperty("user.dir") + File.separator + relativePath;

            File dest = new File(absolutePath);
            dest.getParentFile().mkdirs();
            Files.copy(src.toPath(), dest.toPath());

            // ✅ Return ABSOLUTE path for ExtentReports
            return absolutePath.replace("\\", "/");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public static void attachToReport(WebDriver driver, String name) {
        
        String path = capture(driver, name);
        if (path != null) {
            ExtentReportManager.getTest().info("📸 Screenshot: " + name,
                    MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        }
    }
}
