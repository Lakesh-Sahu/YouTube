package pages;

import org.apache.logging.log4j.Level;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.CommonMethods;

import java.time.Duration;
import java.util.List;

import static utility.Base.*;

public class SearchResult {
    WebDriver driver;
    WebDriverWait wait;
    CommonMethods cm;

    public SearchResult(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(15));
        cm = new CommonMethods(this.driver);
    }

    // Searching for given text
    public boolean search(String text) {
        try {
            WebElement searchBar = cm.findElementVisi("//input[@placeholder='Search']");
            boolean status = cm.sendKeys(searchBar, text);
            searchBar.sendKeys(Keys.ENTER);
            return status;
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while searching in the search bar");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while searching in the search bar", e, Level.WARN);
            return false;
        }
    }

    // Scrolling until total view count of videos is greater than or equal to the targetViewCount
    public boolean scrollTillViewCount(long targetViewCount) {
        try {
            long totalViewCount = 0;
            int totalElementRendered = 0;

            while (true) {

                // Locating and storing the view count Web Element
                List<WebElement> views = cm.findElementsVisi("(//ytd-video-renderer//span[contains(text(),'views')])[position() >" + totalElementRendered + "]");

                // Converting the view count from K, lakh, crore, M, B into digit and adding to totalViewCount
                for (WebElement element : views) {

                    // Getting the view count as String
                    String viewString = cm.getText(element);

                    // Getting the Integer part from the String
                    double count = intViewCountSeperator(viewString);

                    if (viewString.contains("K"))
                        count = count * 1000;
                    else if (viewString.contains("lakh"))
                        count *= 100000;
                    else if (viewString.contains("crore"))
                        count *= 10000000;
                    else if (viewString.contains("M"))
                        count *= 1000000;
                    else if (viewString.contains("B"))
                        count *= 1000000000;

                    totalViewCount = totalViewCount + (long) count;
                }

                if (totalViewCount >= targetViewCount)
                    return true;

                // Storing the last position of rendered
                totalElementRendered += views.size();

                // Scrolling to the last views element so that new section can render
                cm.scrollTO(views.get(views.size() - 1));
            }
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while scrolling and counting view");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while scrolling and counting view", e, Level.WARN);
            return false;
        }
    }

    // Separating the Integer part from the view count
    public double intViewCountSeperator(String viewString) {
        try {
            if(viewString == null || viewString.isBlank()) {
                return 0;
            }

            int i = 0;
            for (; i < viewString.length(); i++) {
                if (!"0123456789.".contains(Character.toString(viewString.charAt(i))))
                    break;
            }
            if(i == 0) {
                return 0;
            }
            return Double.parseDouble(viewString.substring(0, i));

        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while separating Integer from view count String");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while separating Integer from view count String", e, Level.WARN);
            return 0;
        }
    }
}