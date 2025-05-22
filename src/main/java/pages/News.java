package pages;

import org.apache.logging.log4j.Level;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.CommonMethods;

import java.time.Duration;
import java.util.ArrayList;

import static utility.Base.*;

public class News {
    WebDriver driver;
    WebDriverWait wait;
    CommonMethods cm;

    public News(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
        cm = new CommonMethods(this.driver);
    }

    // Print the Title, Body and number of likes in number of given latest News Post cards
    public String getTitleBodyLikesInLatestNewsPostCards(int numberOfNews) {
        try {
            StringBuilder allNewsTitleBodyLike = new StringBuilder();
            // Click Show more button of late news post card
            cm.click(cm.findElementVisi("//span[normalize-space()='Latest news posts']//ancestor::div[@id='dismissible']//span[normalize-space()='Show more']/ancestor::button[@aria-label='Show more']"));

            // Iterating through the number of new card(n) given
            for (int i = 1; i <= numberOfNews; i++) {

                ArrayList<WebElement> allDescriptionsOfSingleNews = new ArrayList<>(cm.findElementsVisi("//span[text()='Latest news posts']/ancestor::div[@id='dismissible']//ytd-rich-item-renderer[" + i + "]//yt-formatted-string[@id='home-content-text']//descendant-or-self::*"));
                // Creating StringBuilder object
                StringBuilder sb = new StringBuilder();

                // Iterating through all stored WebElement
                for (WebElement webElement : allDescriptionsOfSingleNews) {

                    // Getting the text and appending to a StringBuilder object
                    sb.append(cm.getText(webElement)).append(" ");
                }

                // Converting the StringBuilder object to String
                String titleBodyString = sb.toString();

                // Locating the like count
                WebElement like = cm.findElementPres("//span[text()='Latest news posts']/ancestor::div[@id='dismissible']//ytd-rich-item-renderer[" + i + "]//span[@id='vote-count-middle']");

                // Getting the like count as String
                String likeString = cm.getText(like);

                // If like count String is empty then taking it as 0
                if (likeString == null || likeString.isBlank())
                    likeString = "0";

                // Printing all the data
                allNewsTitleBodyLike.append("News ").append(i).append(" : ").append(titleBodyString).append("\n").append("Likes : ").append(likeString.trim()).append("\n\n");
            }
            return allNewsTitleBodyLike.toString();
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while getting News Titles from Latest news post");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while getting News Titles from Latest news post", e, Level.WARN);
            return "";
        }
    }
}