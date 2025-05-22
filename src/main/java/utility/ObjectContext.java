package utility;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import pages.*;

// This class is used to create the Object of classes per thread for parallel execution
public class ObjectContext extends Base{
    private final WebDriver driver;
    private final String className;
    private final String methodName;

    public About about;
    public Home home;
    public Movies movies;
    public Music music;
    public News news;
    public SearchResult searchResult;
    public CommonMethods cm;
    public ExtentTest test;

    public ObjectContext(WebDriver driver, String className, String methodName, String description) {
        this.driver = driver;
        this.className = className;
        this.methodName = methodName;

        about = new About(driver);
        home = new Home(driver);
        movies = new Movies(driver);
        music = new Music(driver);
        news = new News(driver);
        searchResult = new SearchResult(driver);
        cm = new CommonMethods(driver);

        test = reports.createTest(className + " " + methodName + " " + description);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getClassNameByUser() {
        return className;
    }

    public String getMethodNameByUser() {
        return methodName;
    }
}