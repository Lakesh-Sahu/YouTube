package utility;

import org.apache.logging.log4j.Level;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CommonMethods extends Base {
    WebDriver driver;
    WebDriverWait wait;

    public CommonMethods(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean click(WebElement we) {
        try {
            scrollTO(we);
            we.click();
            Thread.sleep(1000);
            return true;
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while clicking");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while clicking", e, Level.WARN);
            return false;
        }
    }

    public boolean sendKeys(WebElement we, String keyToSend) {
        try {
            we.clear();
            Thread.sleep(250);
            we.sendKeys(keyToSend);
            Thread.sleep(250);
            return true;
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while sending keys " + keyToSend);
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while sending keys " + keyToSend, e, Level.WARN);
            return false;
        }
    }

    // Scroll to a particular element using java script executor
    public void scrollTO(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView({behavior : 'smooth', block : 'center', inline : 'center'});", element);
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while scrolling to a WebElement");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while scrolling to a WebElement", e, Level.WARN);
        }
    }

    public String getCurrentUrl() {
        try {
            return driver.getCurrentUrl();
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while getting current url");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while getting current url", e, Level.WARN);
            return "";
        }
    }

    public String getText(WebElement we) {
        try {
            scrollTO(we);
            return we.getText();
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while scrolling and getting text from WebElement");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while scrolling and getting text from WebElement", e, Level.WARN);
            return "";
        }
    }

    public String generateDynamicUserName(String username) {
        try {
            Random rm = new Random();
            int rmInt = rm.nextInt(0, 9999);
            // Getting time stamp for generating a unique username
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            // Concatenate the timestamp to string to form unique timestamp
            return username + "_" + timestamp.getTime() + rmInt;
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while generating dynamic user name for username " + username);
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while generating dynamic user name for username " + username, e, Level.WARN);
            return "";
        }
    }

    public boolean compareString(String s1, String s2) {
        try {
            return s1.replaceAll(" ", "").equalsIgnoreCase(s2.replaceAll(" ", ""));
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while comparing two strings");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while comparing two strings", e, Level.WARN);
            return false;
        }
    }

    public boolean compareEqualString(String s1, String s2) {
        return s1.equalsIgnoreCase(s2);
    }

    public boolean compareContainsString(String s1, String s2) {
        return s1.toLowerCase().contains(s2.toLowerCase());
    }

    public boolean navigateBack() {
        try {
            driver.navigate().back();
            return true;
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while navigating back");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while navigating back", e, Level.WARN);
            return false;
        }
    }

    public boolean closeWindow() {
        try {
            driver.close();
            return true;
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while closing window");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while closing window", e, Level.WARN);
            return false;
        }
    }

    // Separating Starting Integer from String
    public int getStartingIntFromString(String intString) {
        int a = 0;
        for (int i = 0; i < intString.length(); i++) {
            if (!Character.isDigit(intString.charAt(i))) {
                break;
            }
            a++;
        }
        return Integer.parseInt(intString.substring(0, a));
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

    //    Waits until a single visible or enabled child element is found inside a given parent element using the specified XPath; uses findElement and checks isDisplayed().
    public WebElement findElementFromParentByXPath(WebDriverWait wait, WebElement parentElement, String childXPath) {
        return wait.until(driver -> {
            WebElement child = parentElement.findElement(By.xpath(childXPath));
            try {
                return child.isDisplayed() || child.isEnabled() ? child : null;
            } catch (Exception e) {
                logWarningInExtentReport(e, "Exception while finding element from parent by xpath");
                logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while finding element from parent by xpath", e, Level.WARN);
                return null;
            }
        });
    }

    public WebElement findElementFromParentByClassName(WebDriverWait wait, WebElement parentElement, String childClassName) {
        return wait.until(driver -> {
            WebElement child = parentElement.findElement(By.className(childClassName));
            try {
                return child.isDisplayed() || child.isEnabled() ? child : null;
            } catch (Exception e) {
                logWarningInExtentReport(e, "Exception while finding element from parent by className");
                logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while finding element from parent by className", e, Level.WARN);
                return null;
            }
        });
    }

    public WebElement findElementFromParentByTagName(WebDriverWait wait, WebElement parentElement, String childTagName) {
        return wait.until(driver -> {
            WebElement child = parentElement.findElement(By.tagName(childTagName));
            try {
                return child.isDisplayed() || child.isEnabled() ? child : null;
            } catch (Exception e) {
                logWarningInExtentReport(e, "Exception while finding element from parent by tagName");
                logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while finding element from parent by tagName", e, Level.WARN);
                return null;
            }
        });
    }

    //    Waits until at least one matching child element is found inside a given parent element using the specified XPath; uses findElements and returns the list
    public List<WebElement> findElementsFromParentByXPath(WebDriverWait wait, WebElement parentElement, String childXPath) {
        try {
            return wait.until(driver -> {
                List<WebElement> children = parentElement.findElements(By.xpath(childXPath));
                return children.isEmpty() ? null : children;
            });
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while finding elements from parent by xpath");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while finding elements from parent by xpath", e, Level.WARN);
            return null;
        }
    }

    public List<WebElement> findElementsFromParentByClassName(WebDriverWait wait, WebElement parentElement, String childClassName) {
        try {
            return wait.until(driver -> {
                List<WebElement> children = parentElement.findElements(By.className(childClassName));
                return children.isEmpty() ? null : children;
            });
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while finding elements from parent by className");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while finding elements from parent by className", e, Level.WARN);
            return null;
        }
    }

    public List<WebElement> findElementsFromParentByTagName(WebDriverWait wait, WebElement parentElement, String childTagName) {
        try {
            return wait.until(driver -> {
                List<WebElement> children = parentElement.findElements(By.tagName(childTagName));
                return children.isEmpty() ? null : children;
            });
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while finding elements from parent by tagName");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while finding elements from parent by tagName", e, Level.WARN);
            return null;
        }
    }

    public String getWindowHandle() {
        try {
            return driver.getWindowHandle();
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while getting window handle");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while getting window handle", e, Level.WARN);
            return "";
        }
    }

    public Set<String> getWindowHandles() {
        try {
            return driver.getWindowHandles();
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while getting window handles");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while getting window handles", e, Level.WARN);
            return new HashSet<>();
        }
    }

    public boolean switchToWindow(String windowHandel) {
        try {
            return driver.switchTo().window(windowHandel) != null;
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while switching window");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while switching window", e, Level.WARN);
            return false;
        }
    }

    public boolean switchAndCloseTheWindow(String windowHandel) {
        try {
            driver.switchTo().window(windowHandel).close();
            return true;
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while switching and closing window");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while switching and closing window", e, Level.WARN);
            return false;
        }
    }

    public boolean switchToIFrameByIndex(int iframeIndex) {
        try {
            return driver.switchTo().frame(iframeIndex) != null;
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while switching to iframe by index");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while switching to iframe by index", e, Level.WARN);
            return false;
        }
    }

    public boolean switchToIFrameByNameOrId(String nameOrId) {
        try {
            return driver.switchTo().frame(nameOrId) != null;
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while switching to iframe by name or id");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while switching to iframe by name or id", e, Level.WARN);
            return false;
        }
    }

    public boolean switchToIFrameByWebElement(WebElement iframeWebElement) {
        try {
            return driver.switchTo().frame(iframeWebElement) != null;
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while switching to iframe by iframe web element");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while switching to iframe by iframe web element", e, Level.WARN);
            return false;
        }
    }

    public boolean switchToParentFrame() {
        try {
            return driver.switchTo().parentFrame() != null;
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while switching to parent frame of iframe");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while switching to parent frame of iframe", e, Level.WARN);
            return true;
        }
    }

    public boolean switchToDefaultContent() {
        try {
            return driver.switchTo().defaultContent() != null;
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while switching to default content of iframe");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while switching to default content of iframe", e, Level.WARN);
            return true;
        }
    }
}