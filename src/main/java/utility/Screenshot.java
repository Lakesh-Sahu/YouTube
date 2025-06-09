package utility;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.util.Random;

public class Screenshot extends Base {

    // takes the screenshot to attach in the Extent Report
    public static String capture(String callerInfo) {
        try {
            WebDriver driver = ObjectManager.getContext().getDriver();
            int randomNum = new Random().nextInt(0, 50);

            if (driver != null) {
                String timestamp = String.valueOf(java.time.LocalDateTime.now()).replaceAll("[.:]", "");
                String relativePath = "/extent_reports/" + singleTimeStamp + "/" + timestamp + randomNum + "_" + callerInfo + ".png";
                TakesScreenshot scrShot = ((TakesScreenshot) driver);
                File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
                File DestFile = new File(System.getProperty("user.dir") + relativePath);
                FileUtils.copyFile(SrcFile, DestFile);
                return relativePath;
            }
        } catch (Exception e) {
            ObjectManager.getContext().test.warning(getCallerInfoFromStackTrace(e.getStackTrace()) + " Taking screenshot : Fail : " + getMessageFromException(e));
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Taking screenshot : Fail", e, Level.WARN);
        }
        return "";
    }
}