package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    private static ExtentReports extent;
    private static ExtentSparkReporter spark;
    private static final ThreadLocal<ExtentTest> scenarioThread = new ThreadLocal<>();
    private static String reportPath;
    private static final long startTime = System.currentTimeMillis(); // ⏱️ Total test start time

    private static String getReportPath() {
        reportPath = "test-output/ExtentReport_" + getTimeStamp() + ".html";
        return reportPath;
    }

    public static ExtentReports getInstance() {
        if (extent == null) {
            spark = new ExtentSparkReporter(getReportPath());

            // 🌈 Updated Theme and Report Title/Name
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle("WTC IPACS Test Execution Report");
            spark.config().setReportName("WTC IPACS Test Results - " + getTimeStamp());

            // 🎯 UI Customization
            spark.config().setEncoding("utf-8");
            spark.config().setTimelineEnabled(true);
            spark.config().setCss(".category-section { display: none !important; }"); // Hide left sidebar
            spark.config().setJs("document.addEventListener('DOMContentLoaded', function() {" +
                    "document.querySelectorAll('.node').forEach(n => n.classList.add('is-expanded'));" +
                    "});");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Environment", "UAT");
            extent.setSystemInfo("Tester", "Automation Team");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
        return extent;
    }

    public static void createScenario(String scenarioName) {
        ExtentTest scenario = getInstance().createTest(scenarioName);
        scenarioThread.set(scenario);
    }

    public static ExtentTest getTest() {
        return scenarioThread.get();
    }

    public static void logInfo(String message) {
        if (getTest() != null) getTest().info("ℹ️ " + message);
    }

    public static void logPass(String message) {
        if (getTest() != null) getTest().pass("✅ " + message);
    }

    public static void logFail(String message) {
        if (getTest() != null) getTest().fail("❌ " + message);
    }

    public static void logSkip(String message) {
        if (getTest() != null) getTest().skip("⚠️ " + message);
    }

    public static void attachScreenshot(WebDriver driver, String title) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String fileName = title.replaceAll("\\s+", "_") + "_" + getTimeStamp() + ".png";
            String relativePath = "test-output/screenshots/" + fileName;
            String absolutePath = System.getProperty("user.dir") + File.separator + relativePath;

            File dest = new File(absolutePath);
            dest.getParentFile().mkdirs();
            Files.copy(src.toPath(), dest.toPath());

            if (getTest() != null) {
                getTest().info("📸 Screenshot: " + title,
                        MediaEntityBuilder.createScreenCaptureFromPath(relativePath.replace("\\", "/")).build());
            }
        } catch (IOException e) {
            if (getTest() != null) {
                getTest().fail("⚠️ Screenshot capture failed: " + e.getMessage());
            }
        }
    }

    public static void logScenarioTime(long durationInMillis) {
        double seconds = durationInMillis / 1000.0;
        logInfo("🕒 Scenario run time: " + seconds + " seconds");
    }

    public static void flushReport() {
        if (extent != null) {
            long endTime = System.currentTimeMillis();
            double totalDuration = (endTime - startTime) / 1000.0;
            extent.setSystemInfo("Total Execution Time", totalDuration + " seconds");

            extent.flush();
            String fullPath = System.getProperty("user.dir") + File.separator + reportPath;
            System.out.println("📄 Extent Report generated at: " + fullPath);
            try {
                File htmlFile = new File(fullPath);
                if (htmlFile.exists()) {
                    java.awt.Desktop.getDesktop().browse(htmlFile.toURI());
                }
            } catch (IOException e) {
                System.err.println("⚠️ Could not open report in browser: " + e.getMessage());
            }
        }
    }

    private static String getTimeStamp() {
        return new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
    }
}
