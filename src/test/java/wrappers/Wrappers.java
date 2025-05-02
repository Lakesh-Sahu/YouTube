package wrappers;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.time.Duration;
import java.util.List;

public class Wrappers {

    ChromeDriver driver;
    WebDriverWait wait;

    public void setDriverAndWait(ChromeDriver driver, int duration) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
    }

    // Open 'YouTube' url
    public boolean openUrl() {
        try {
            driver.get("https://www.youtube.com/");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Click on an element
    public boolean click(WebElement element) {
        try {
            scrollTO(element);
            Thread.sleep(250);
            element.click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Send message on an element
    public boolean sendKeys(WebElement element, String text) {
        try {
            scrollTO(element);
            Thread.sleep(250);
            element.clear();
            Thread.sleep(250);
            element.sendKeys(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Get the current url of web page
    public String getUrl() {
        try {
            return driver.getCurrentUrl();
        } catch (Exception e) {
            return "";
        }
    }

    // Scroll to a particular element using java script executor
    public void scrollTO(WebElement element) {
        try {
        JavascriptExecutor js = driver;
        js.executeScript("arguments[0].scrollIntoView({behavior : 'smooth', block : 'center', inline : 'center'});", element);
        } catch (Exception ignored) {

        }
    }

    // Locate an element using wait, xpath and Expected Conditions of
    // visibilityOfElementLocated
    public WebElement findElementVisi(String xpath) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    // Locate an element using wait, xpath and Expected Conditions of presenceOfElementLocated
    public WebElement findElementPres(String xpath) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    // Locate an element using max required wait, xpath and Expected Conditions of visibilityOfElementLocated
    public WebElement findElementVisi(String xpath, int waitTime) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    // Locate an element using max required wait, xpath and Expected Conditions of presenceOfElementLocated
    public WebElement findElementPres(String xpath, int waitTime) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    // Locate elements using wait, xpath and Expected Conditions of visibilityOfElementLocated
    public List<WebElement> findElementsVisi(String xpath) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return driver.findElements(By.xpath(xpath));
    }

    // Separating Starting Integer from String
    public int intFromString(String intString) {
        int a = 0;
        String b = "0123456789";
        for (int i = 0; i < intString.length(); i++) {
            if (!b.contains(String.valueOf(intString.charAt(i)))) {
                break;
            }
            a++;
        }
        return Integer.parseInt(intString.substring(0, a));
    }

    public boolean clickAboutButton() {
        try {
            return click(findElementVisi("//a[text()='About']"));
        } catch (Exception e) {
            return false;
        }
    }

    // Waiting for about content to show to confirm page is open fully
    public boolean waitForAboutToLoad() {
        try {
            return findElementVisi("//section[@class='ytabout__content']/p[1]").isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean clickMobieBtn() {
        try {
            return click(findElementVisi("//yt-formatted-string[text()='Movies']"));
        } catch (Exception e) {
            return false;
        }
    }

    // Locating and clicking the 'Next' button till it reaches last
    public boolean clickingNextBtnInMovies() {
        try {
            WebElement nextBtn = findElementVisi("//span[text()='Top selling']/ancestor::div[@id='dismissible']//button[@aria-label='Next']", 5);
            while (nextBtn.isDisplayed()) {
                click(nextBtn);
                Thread.sleep(1000);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Locating the age category text of last movie
    public String getLastMovieAgeRating() {
        try {
            return findElementVisi("(//span[text()='Top selling']/ancestor::div[@id='primary']//ytd-grid-movie-renderer[last()]/ytd-badge-supported-renderer//p)[2]").getText();
        } catch (Exception e) {
            return "";
        }
    }

    // Locating the age category text of last movie
    public String getLastMovieCategory() {
        try {
            return findElementVisi("//span[text()='Top selling']/ancestor::div[@id='primary']//ytd-grid-movie-renderer[last()]/a/span").getText();
        } catch (Exception e) {
            return "";
        }
    }

    // Locating and clicking 'Music' button
    public boolean clickMusicBtn() {
        try {
            return click(findElementVisi("//yt-formatted-string[text()='Music']"));
        } catch (Exception e) {
            return false;
        }
    }

    // Locating 'Show more' button in the first section and clicking until it reaches last
    public boolean clickingShowMoreBtn() {
        try {
            WebElement showMoreBtn = findElementVisi("//ytd-rich-section-renderer[1]//span[normalize-space()='Show more']//ancestor::button");
            while (showMoreBtn.isDisplayed()) {
                click(showMoreBtn);
                Thread.sleep(1000);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Locating, getting and printing the last playlist name
    public String getLastMusicPlaylistName() {
        try {
            return findElementVisi("//ytd-rich-section-renderer[1]//ytd-rich-item-renderer[last()]//h3//span", 5).getText();
        } catch (Exception e) {
            return "";
        }
    }

    // Locating and storing total number of track songs present in the last music card
    public int getSongCountOfFirstSectionLastPlaylist() {
        try {
            WebElement songCount = findElementVisi(
                    "//ytd-rich-section-renderer[1]//ytd-rich-item-renderer[last()]//div[@class='badge-shape-wiz__text']",
                    5);
            return intFromString(songCount.getText());
        } catch (Exception e) {
            return 0;
        }
    }

    // Locating and clicking the 'News' button
    public boolean clickNewsBtn() {
        try {
            return click(findElementVisi("//yt-formatted-string[text()='News']"));
        } catch (Exception e) {
            return false;
        }
    }

    // Print the Title, Body and number of likes in number of given latest News Post cards
    public boolean printTitleBodyLikesInLatestNewsPostCards(int numberOfNews) {
        try {
            // Click Show more button of late news post card
            click(findElementVisi("//span[normalize-space()='Latest news posts']//ancestor::div[@id='dismissible']//span[normalize-space()='Show more']/ancestor::button[@aria-label='Show more']"));

            // Iterating through the number of new card(n) given
            for (int i = 1; i <= numberOfNews; i++) {

                ArrayList<WebElement> allDescriptions = new ArrayList<>(findElementsVisi("//span[text()='Latest news posts']/ancestor::div[@id='dismissible']//ytd-rich-item-renderer[" + i + "]//yt-formatted-string[@id='home-content-text']//descendant-or-self::*"));
                // Creating StringBuilder object
                StringBuilder sb = new StringBuilder();

                // Iterating through all stored WebElement
                for (WebElement webElement : allDescriptions) {

                    // Getting the text and appending to a StringBuilder object
                    sb.append(webElement.getText()).append(" ");
                }

                // Converting the StringBuilder object to String
                String titleBodyString = sb.toString();

                // Locating the like count
                WebElement like = findElementPres("//span[text()='Latest news posts']/ancestor::div[@id='dismissible']//ytd-rich-item-renderer[" + i + "]//span[@id='vote-count-middle']");

                // Getting the like count as String
                String likeString = like.getText();

                // If like count String is empty then taking it as 0
                if (likeString == null || likeString.isBlank())
                    likeString = "0";

                // Printing all the data
                System.out.println("News " + i + " : " + titleBodyString);
                System.out.println("Likes : " + likeString.trim());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Searching for given text
    public boolean search(String text) {
        try {
            WebElement searchBar = findElementVisi("//input[@placeholder='Search']");
            boolean status = sendKeys(searchBar, text);
            searchBar.sendKeys(Keys.ENTER);
            return status;
        } catch (Exception e) {
            return false;
        }
    }

    // Scrolling until total view count of videos is greater than or equal to the targetViewCount
    public boolean viewCount(long targetViewCount) {
        try {
            long totalViewCount = 0;
            int totalElementRendered = 0;

            while (true) {

                // Locating and storing the view count Web Element
                List<WebElement> views = findElementsVisi("(//ytd-video-renderer//span[contains(text(),'views')])[position() >" + totalElementRendered + "]");

                // Converting the view count from K, lakh, crore, M, B into digit and adding to totalViewCount
                for (WebElement element : views) {

                    // Getting the view count as String
                    String viewString = element.getText();

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
                totalElementRendered = views.size();

                // Scrolling to the last views element so that new section can render
                scrollTO(views.get(totalElementRendered - 1));
            }
        } catch (Exception e) {
            return false;
        }
    }

    // Separating the Integer part from the view count
    public double intViewCountSeperator(String viewString) {
        int i = 0;
        for (; i < viewString.length(); i++) {
            if (!"0123456789.".contains(Character.toString(viewString.charAt(i))))
                break;
        }
        return Double.parseDouble(viewString.substring(0, i));
    }

    public boolean compareEqualString(String s1, String s2) {
        return s1.equalsIgnoreCase(s2);
    }

    public boolean compareContainsString(String s1, String s2) {
        return s1.toLowerCase().contains(s2.toLowerCase());
    }
}