package pages;

import org.apache.logging.log4j.Level;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.CommonMethods;

import java.time.Duration;

import static utility.Base.*;

public class Home {
    WebDriver driver;
    WebDriverWait wait;
    CommonMethods cm;

    // Home page url
    private final String url = "https://www.youtube.com/";
    private final String url2 = "https://www.youtube.com/?themeRefresh=1";

    public Home(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(15));
        cm = new CommonMethods(this.driver);
    }

    public boolean verifyOnHomePage() {
        try {
            return wait.until(ExpectedConditions.or(ExpectedConditions.urlToBe(url), ExpectedConditions.urlToBe(url2)));
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while verifying on Home Page");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while verifying on Home Page", e, Level.WARN);
            return false;
        }
    }

    // Navigating to the Home Page
    public boolean navigateToHome() {
        try {
            driver.get(url);
            return true;
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while navigating to Home Page");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while navigating to Home Page", e, Level.WARN);
            return false;
        }
    }

    public String getHomePageUrl() {
        try {
            return url;
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while getting Home Page url");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while getting Home Page url", e, Level.WARN);
            return "";
        }
    }

    public boolean clickAboutButton() {
        try {
            return cm.click(cm.findElementVisi("//a[text()='About']"));
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while clicking About button");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while clicking About button", e, Level.WARN);
            return false;
        }
    }

    public boolean clickMoviesBtn() {
        try {
            try {
                return cm.click(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//yt-formatted-string[text()='Movies']"))));
            } catch (Exception ignore) {
            }
            return cm.click(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//yt-formatted-string[text()='Films']"))));
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while clicking Movies or Films button");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while clicking Movies or Films button", e, Level.WARN);
            return false;
        }
    }

    // Locating and clicking 'Music' button
    public boolean clickMusicBtn() {
        try {
            return cm.click(cm.findElementVisi("//yt-formatted-string[text()='Music']"));
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while clicking Music button");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while clicking Music button", e, Level.WARN);
            return false;
        }
    }

    // Locating and clicking the 'News' button
    public boolean clickNewsBtn() {
        try {
            return cm.click(cm.findElementVisi("//yt-formatted-string[text()='News']"));
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while clicking News button");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while clicking News button", e, Level.WARN);
            return false;
        }
    }
}