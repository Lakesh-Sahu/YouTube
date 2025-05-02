package testcases;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import utils.DriverFactory;
import utils.ExcelDataProvider;
import wrappers.Wrappers;

public class TestCases extends Wrappers {
    ChromeDriver driver;

    /*
    Go to https://www.youtube.com/ and Assert user is on the correct URL.
    Click on "About" at the bottom of the sidebar, and print the message on the screen.
    */
    @Test(enabled = true)
    public void testCase01() {

        try {
            System.out.println("Start of testCase01");

            // Opening YouTube url
            Assert.assertTrue(openUrl(), "User is unable to open url");

            // Asserting that user is on landing on correct url
            Assert.assertEquals(getUrl(), "https://www.youtube.com/", "User is not on https://www.youtube.com/ page");

            // Clicking on "About" button
            Assert.assertTrue(clickAboutButton(), "User is unable to click about button");

            // Waiting for about content to show to confirm page is open fully
            Assert.assertTrue(waitForAboutToLoad(), "User is unable to see the About message on about page");

            // Asserting that we are on about page
            Assert.assertTrue(compareContainsString(getUrl(), "about"), "User is not on about page");

            // Storing and printing the message shown on about page
            List<WebElement> textElements = findElementsVisi("//section[@class='ytabout__content']/*");
            for (WebElement message : textElements) {
                System.out.println(message.getText());
            }

            System.out.println("testCase01 Passed");

        } catch (Exception e) {
            System.out.println("testCase01 Failed");
        }
        System.out.println("End of testCase01");
    }

    /*
     Go to the "Films" or "Movies" tab and in the “Top Selling” section, scroll
     to the extreme right. Apply a Soft Assert on whether the movie is marked “A”
     for Mature or not. Apply a Soft assert on the movie category to check if it
     exists ex: "Comedy", "Animation", "Drama".
     */
    @Test(enabled = true)
    public void testCase02() {
        try {
            System.out.println("Start of testCase02");

            // Opening YouTube url
            Assert.assertTrue(openUrl(), "User is unable to open url");

            // Locating and clicking 'Movies' button
            Assert.assertTrue(clickMobieBtn(), "User is unable to click movies button");

            // Locating and clicking the 'Next' button till it reaches last
            Assert.assertTrue(clickingNextBtnInMovies(), "User is unable to click Next button in Movies section until it reaches last");

            // Creating object of 'SoftAssert' class
            SoftAssert sa = new SoftAssert();

            // Locating the age category text of last movie and asserting that it contains A or U/A
            String lastMovieAgeRating = getLastMovieAgeRating();
            sa.assertTrue(compareContainsString(lastMovieAgeRating, "A") || compareContainsString(lastMovieAgeRating, "U/A") || compareContainsString(lastMovieAgeRating, "U/A"), "Movies does not contain A or U or U/A age tag");

            // Locating that comedy, animation or any other category is present in the last movie
            sa.assertNotEquals(getLastMovieCategory().length(), 0, "There is no category present in the last movie card");

            // Asserting all the soft assert
            sa.assertAll();

            System.out.println("testCase02 Passed");

        } catch (Exception e) {
            System.out.println("testCase02 Failed");
        }
        System.out.println("End of testCase02");
    }

    /*
     Go to the "Music" tab and in the 1st section, scroll to the last content of 1st section.
     Print the name of the playlist. Soft Assert on whether the number of tracks
     listed is less than or equal to 50.
     */
    @Test(enabled = true)
    public void testCase03() {
        try {
            System.out.println("Start of testCase03");

            // Creating object of 'SoftAssert'
            SoftAssert sa = new SoftAssert();

            // Opening 'YouTube' url
            Assert.assertTrue(openUrl(), "User is unable to open url");

            // Locating and clicking 'Music' button
            Assert.assertTrue(clickMusicBtn(), "User is unable to click Music button");

            // Locating 'Show more' button in the first section and clicking until it reaches last
            Assert.assertTrue(clickingShowMoreBtn(), "User is unable to click show more button in Music section until it reaches last");

            // Locating, getting and printing the last playlist name
            String lastMusicPlaylistName = getLastMusicPlaylistName();
            Assert.assertNotNull(lastMusicPlaylistName, "Last Music playlist is null");
            System.out.println("Playlist Name : " + lastMusicPlaylistName);

            // Soft Asserting that total track count is less than or equal to 50
            int songCountOfFirstSectionLastPlaylist = getSongCountOfFirstSectionLastPlaylist();
            sa.assertTrue(getSongCountOfFirstSectionLastPlaylist() <= 50, "Video Count is greater than 50 : " + songCountOfFirstSectionLastPlaylist);

            // Asserting all the soft assert
            sa.assertAll();

            System.out.println("testCase03 Passed");

        } catch (Exception e) {
            System.out.println("testCase03 Failed");
        }
        System.out.println("End of testCase03");
    }

    /*
     Go to the "News" tab and print the title and body of the 1st 3 “Latest News
     Posts” along with the sum of the number of likes on all 3 of them. No likes
     given means 0.
     */
    @Test(enabled = true)
    public void testCase04() {
        try {
            System.out.println("Start of testCase04");

            // Opening 'YouTube' url
            Assert.assertTrue(openUrl(), "User is unable to open url");

            // Asserting that user is on landing on correct url
            Assert.assertEquals(getUrl(), "https://www.youtube.com/", "User is not on https://www.youtube.com/ page");

            // Locating and clicking the 'News' button
            Assert.assertTrue(clickNewsBtn(), "User is unable to click news button");

            int numberOfNews = 3;
            // Getting the Title, Body and number of likes in News cards
            Assert.assertTrue(printTitleBodyLikesInLatestNewsPostCards(numberOfNews), "User is unable to see " + numberOfNews + " news in the latest news post");

            System.out.println("testCase04 Passed");
        } catch (Exception e) {
            System.out.println("testCase04 Failed");
        }
        System.out.println("End of testCase04");
    }

    /*
     Search for each of the items given in the stubs:
     src/test/resources/data.xlsx, and keep scrolling till the sum of each video’s
     views reach 10 Cr.
     */
    @Test(enabled = true, dataProvider = "excelData", dataProviderClass = ExcelDataProvider.class)
    public void testCase05(String text) {
        try {
            System.out.println("Start of testCase05");

            // Opening 'YouTube' url
            Assert.assertTrue(openUrl(), "User is unable to open url");

            // Asserting that user is on landing on correct url
            Assert.assertEquals(getUrl(), "https://www.youtube.com/", "User is not on https://www.youtube.com/ page");

            // Searching for text provided by the dataProvider
            Assert.assertTrue(search(text), "User is unable to search in the search box");

            // Scrolling until total view count of videos is greater than or equal to the targetViewCount
            long viewCount = 100000000L;
            Assert.assertTrue(viewCount(viewCount), "User is unable to scroll until it reached " + viewCount + " view count");

            System.out.println("testCase05 Passed");
        } catch (Exception e) {
            System.out.println("testCase05 Failed");
        }
        System.out.println("End of testCase05");
    }

    @BeforeTest
    public void startBrowser() {
        driver = DriverFactory.getDriver();
        setDriverAndWait(driver, 15);
    }

    // Quiting the browser
    @AfterTest
    public void endTest() {
        try {
            driver.quit();
            System.out.println("Quiting Complete");
        } catch (Exception e) {
            System.out.println("Quiting Failed");
        }
    }
}