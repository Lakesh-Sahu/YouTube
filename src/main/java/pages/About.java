package pages;

import org.apache.logging.log4j.Level;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.CommonMethods;

import java.time.Duration;

import static utility.Base.*;

public class About {
    WebDriver driver;
    WebDriverWait wait;
    CommonMethods cm;

    // Thanks page url
    String url = "https://about.youtube/";

    public About(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        cm = new CommonMethods(this.driver);
    }

    public boolean verifyOnAboutPage() {
        try {
            return wait.until(ExpectedConditions.urlToBe(url));
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while verifying on About Page");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while verifying on About Page", e, Level.WARN);
            return false;
        }
    }

    // Waiting for about content to show to confirm page is open fully
    public boolean waitForAboutToLoad() {
        try {
            return cm.findElementVisi("//section[@class='ytabout__content']/p[1]").isDisplayed();
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while waiting for about page to load");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while waiting for about page to load", e, Level.WARN);
            return false;
        }
    }
}
