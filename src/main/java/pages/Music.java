package pages;

import org.apache.logging.log4j.Level;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.CommonMethods;

import java.time.Duration;

import static utility.Base.*;

public class Music {
    WebDriver driver;
    WebDriverWait wait;
    CommonMethods cm;

    public Music(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(15));
        cm = new CommonMethods(this.driver);
    }

    // Locating 'Show more' button in the first section and clicking until it reaches last
    public boolean clickingShowMoreBtn() {
        try {
            WebElement showMoreBtn = cm.findElementVisi("//ytd-rich-section-renderer[1]//span[normalize-space()='Show more']//ancestor::button");
            while (showMoreBtn.isDisplayed()) {
                cm.click(showMoreBtn);
                Thread.sleep(1000);
            }
            return true;
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while clicking 'ShowMore' button in Music");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while clicking 'ShowMore' button in Music", e, Level.WARN);
            return false;
        }
    }

    // Locating, getting and printing the last playlist name
    public String getLastMusicPlaylistName() {
        try {
            WebElement lastPlayList = cm.findElementVisi("//ytd-rich-section-renderer[1]//ytd-rich-item-renderer[last()]//h3//span", 5);
            return cm.getText(lastPlayList);
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while getting last music playlist name");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while getting last music playlist name", e, Level.WARN);
            return "";
        }
    }

    // Locating and storing total number of track songs present in the last music card
    public int getSongCountOfFirstSectionLastPlaylist() {
        try {
            WebElement songCount = cm.findElementVisi(
                    "//ytd-rich-section-renderer[1]//ytd-rich-item-renderer[last()]//div[@class='badge-shape-wiz__text']",
                    5);
            return cm.getStartingIntFromString(cm.getText(songCount));
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while getting song count of first section last Playlist");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while getting song count of first section last Playlist", e, Level.WARN);
            return 0;
        }
    }
}