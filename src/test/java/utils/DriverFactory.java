package utils;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import java.util.logging.Level;

public class DriverFactory {
    private static ChromeDriver driver;

    private DriverFactory() {
    }

    public static ChromeDriver getDriver() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // Creating object of ChromeOptions class
        ChromeOptions options = new ChromeOptions();

        // Creating object of LoggingPreferences
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);

        // Setting up the required options
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        // Creating object of ChromeDriver class with options as argument
        driver = new ChromeDriver(options);

        // Maximizing the browser window
        driver.manage().window().maximize();

        return driver = driver == null ? new ChromeDriver(options) : driver;
    }
}