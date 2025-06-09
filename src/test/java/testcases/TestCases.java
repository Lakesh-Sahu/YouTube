package testcases;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import utility.Base;
import utility.ObjectManager;
import utility.ExcelDataProvider;
import utility.ObjectCreator;

import static org.testng.Assert.*;

public class TestCases extends Base {

    @Test(enabled = true, description = "Go to https://www.youtube.com/ and Assert user is on the correct URL. Click on 'About' at the bottom of the sidebar, verify that it contains some message.")
    public void testCase01() {
        ObjectCreator oc = ObjectManager.getContext();

        // Opening YouTube url
        assertTrue(oc.home.navigateToHome(), "User is able to open url");

        // Asserting that user is on landing on correct url
        assertEquals(oc.cm.getCurrentUrl(), "https://www.youtube.com/", "User is on https://www.youtube.com/ page");

        // Clicking on "About" button
        assertTrue(oc.home.clickAboutButton(), "User is able to click about button");

        assertTrue(oc.about.verifyOnAboutPage(), "User is on about page");

        // Waiting for about content to show to confirm page is open fully
        assertTrue(oc.about.waitForAboutToLoad(), "User is able to see the About message on about page");

        // Asserting that we are on about page
        assertTrue(oc.cm.compareContainsString(oc.cm.getCurrentUrl(), "about"), "User is on about page");

        // Storing and verifying the message on about page
        StringBuilder message = new StringBuilder();
        List<WebElement> textElements = oc.cm.findElementsVisi("//section[@class='ytabout__content']/*");
        for (WebElement currMessage : textElements) {
            message.append(oc.cm.getText(currMessage)).append(" ");
        }
        logInfoInExtentReport("About message contains some message : \"" + message.toString() + "\"");
        assertTrue(!message.toString().isBlank(), "About message contains some message : \"" + message.toString() + "\"");
    }

    @Test(enabled = true, timeOut = 120000, dataProvider = "itemToSearchForTotalViewCount", dataProviderClass = ExcelDataProvider.class, description = "Search for each of the items given in the stubs: src/test/resources/data.xlsx, and keep scrolling till the sum of each video’s views reach 10 Cr.")
    public void testCase02(String text) {
        ObjectCreator oc = ObjectManager.getContext();

        logInfoInExtentReport("Performing action for '" + text + "' from excel data");

        // Opening 'YouTube' url
        assertTrue(oc.home.navigateToHome(), "User is able to open url");

        // Asserting that user is on landing on correct url
        assertEquals(oc.cm.getCurrentUrl(), "https://www.youtube.com/", "User is on https://www.youtube.com/ page");

        // Searching for text provided by the dataProvider
        assertTrue(oc.searchResult.search(text), "User is able to search in the search box");

        // Scrolling until total view count of videos is greater than or equal to the targetViewCount
        long viewCount = 100000000L;
        assertTrue(oc.searchResult.scrollTillViewCount(viewCount), "User is able to scroll until it reached " + viewCount + " view count");
    }

    @Test(enabled = true, description = "Go to the 'Films' or 'Movies' tab and in the 'Top Selling' section, scroll to the extreme right. Apply a Soft Assert on whether the movie is marked 'A' or 'U' or 'U/A' tag or not. Apply a Soft assert on the movie category to check if it exists ex: 'Comedy', 'Animation', 'Drama'.")
    public void testCase03() {
        ObjectCreator oc = ObjectManager.getContext();

        // Opening YouTube url
        assertTrue(oc.home.navigateToHome(), "User is able to open url");

        // Locating and clicking 'Movies' button
        assertTrue(oc.home.clickMoviesBtn(), "User is able to click movies button");

        // Locating and clicking the 'Next' button till it reaches last
        assertTrue(oc.movies.clickingNextBtnInMovies(), "User is able to click Next button in Movies section until it reaches last");

        // Creating object of 'SoftAssert' class
        SoftAssert sa = new SoftAssert();

        // Locating the age category text of last movie and asserting that it contains A or U/A
        String lastMovieAgeRating = oc.movies.getLastMovieAgeRating();
        logInfoInExtentReport("Last movie contains age tag of : '" + lastMovieAgeRating + "'");
        sa.assertTrue(oc.cm.compareContainsString(lastMovieAgeRating, "A") || oc.cm.compareContainsString(lastMovieAgeRating, "U") || oc.cm.compareContainsString(lastMovieAgeRating, "U/A"), "Last movie contains age tag of '" + lastMovieAgeRating + "'");

        // Locating that comedy, animation or any other category is present in the last movie
        String lastMovieCategory = oc.movies.getLastMovieCategory();
        logInfoInExtentReport("There is category present in the last movie card : '" + lastMovieCategory + "'");
        sa.assertTrue(!lastMovieCategory.isBlank(), "There is category present in the last movie card : '" + lastMovieAgeRating + "'");

        // Asserting all the soft assert
        sa.assertAll();
    }

    @Test(enabled = true, description = "Go to the 'Music' tab and in the 1st section, scroll to the last content of 1st section. Verify it contains some playlist name. Soft Assert on whether the number of tracks listed is less than or equal to 50.")
    public void testCase04() {
        ObjectCreator oc = ObjectManager.getContext();

        // Creating object of 'SoftAssert'
        SoftAssert sa = new SoftAssert();

        // Opening 'YouTube' url
        assertTrue(oc.home.navigateToHome(), "User is able to open url");

        // Locating and clicking 'Music' button
        assertTrue(oc.home.clickMusicBtn(), "User is able to click Music button");

        // Locating 'Show more' button in the first section and clicking until it reaches last
        assertTrue(oc.music.clickingShowMoreBtn(), "User is able to click show more button in Music section until it reaches last");

        // Locating, getting and verifying the last playlist name
        String lastMusicPlaylistName = oc.music.getLastMusicPlaylistName();
        logInfoInExtentReport("Last Music playlist contains some name : \"" + lastMusicPlaylistName + "\"");
        assertTrue(!lastMusicPlaylistName.isBlank(), "Last Music playlist contains some name : \"" + lastMusicPlaylistName + "\"");

        // Soft Asserting that total track count is less than or equal to 50
        int songCountOfFirstSectionLastPlaylist = oc.music.getSongCountOfFirstSectionLastPlaylist();
        logInfoInExtentReport("Total video track count : " + songCountOfFirstSectionLastPlaylist);
        sa.assertTrue(songCountOfFirstSectionLastPlaylist > 0 && songCountOfFirstSectionLastPlaylist <= 50, "Total video track count is greater than 0 and less than or equal to 50 : " + songCountOfFirstSectionLastPlaylist);

        // Asserting all the soft assert
        sa.assertAll();
    }

    @Test(enabled = true, description = "Go to the 'News' tab and print the title and body of the 1st 3 'Latest News Posts' along with the sum of the number of likes on all 3 of them. No likes given means 0.")
    public void testCase05() {
        ObjectCreator oc = ObjectManager.getContext();

        // Opening 'YouTube' url
        assertTrue(oc.home.navigateToHome(), "User is able to open url");

        // Asserting that user is on landing on correct url
        assertEquals(oc.cm.getCurrentUrl(), "https://www.youtube.com/", "User is on https://www.youtube.com/ page");

        // Locating and clicking the 'News' button
        assertTrue(oc.home.clickNewsBtn(), "User is able to click news button");

        int numberOfNews = 3;
        // Getting the Title, Body and number of likes in News cards
        String allNewsTitleBodyLike = oc.news.getTitleBodyLikesInLatestNewsPostCards(numberOfNews);
        logInfoInExtentReport("News in the latest news post :\n" + allNewsTitleBodyLike);
        assertTrue(!allNewsTitleBodyLike.isBlank(), "User is able to see " + numberOfNews + " news in the latest news post");
    }
}