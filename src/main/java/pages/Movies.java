package pages;

import org.apache.logging.log4j.Level;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.CommonMethods;

import java.time.Duration;

import static utility.Base.*;

public class Movies {
    WebDriver driver;
    WebDriverWait wait;
    CommonMethods cm;

    public Movies(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        cm = new CommonMethods(this.driver);
    }

    // Locating and clicking the 'Next' button till it reaches last
    public boolean clickingNextBtnInMovies() {
        try {
            WebElement nextBtn = cm.findElementVisi("//span[text()='Top selling']/ancestor::div[@id='dismissible']//button[@aria-label='Next']", 5);
            while (nextBtn.isDisplayed()) {
                cm.click(nextBtn);
                Thread.sleep(1000);
            }
            return true;
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while clicking next button in Movies");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while clicking next button in Movies", e, Level.WARN);
            return false;
        }
    }

    // Locating the age category text of last movie
    public String getLastMovieAgeRating() {
        try {
            return cm.getText(cm.findElementVisi("(//span[text()='Top selling']/ancestor::div[@id='primary']//ytd-grid-movie-renderer[last()]/ytd-badge-supported-renderer//p)[2]"));
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while getting last Movie Age Rating");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while getting last Movie Age Rating", e, Level.WARN);
            return "";
        }
    }

    // Locating the age category text of last movie
    public String getLastMovieCategory() {
        try {
            return cm.getText(cm.findElementVisi("//span[text()='Top selling']/ancestor::div[@id='primary']//ytd-grid-movie-renderer[last()]/a/span"));
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while getting last Movie Category");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while getting last Movie Category", e, Level.WARN);
            return "";
        }
    }
}