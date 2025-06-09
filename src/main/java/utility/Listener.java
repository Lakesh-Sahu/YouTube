package utility;

import com.aventstack.extentreports.MediaEntityBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;

public class Listener extends Base implements ITestListener {
    private static final Logger log = LogManager.getLogger(Listener.class);
    public static int count = 1;

    public void onTestStart(ITestResult result) {
        ObjectManager.acquire();

        String className = result.getTestClass().getName();
        String methodName = result.getName();
        String description = result.getMethod().getDescription();

        WebDriver driver = (new utility.DriverFactory()).getDriver(browserName);
        ObjectManager.init(driver, className, methodName, description);
    }

    public void onTestSuccess(ITestResult result) {
        try {
            String callerInfo = getCallerInfoFromITestResult(result, "PASSED");

            ObjectManager.getContext().test.pass(callerInfo, MediaEntityBuilder.createScreenCaptureFromPath(Screenshot.capture(callerInfo)).build());
        } catch (Exception e) {
            System.out.println("#####\nException in onTestSuccess method of Listener class\n" + Arrays.toString(e.getStackTrace()) + "\n#####");
        } finally {
            ObjectManager.release();
            System.out.println("count : " + count++);}
    }

    public void onTestFailure(ITestResult result) {
        try {
            String callerInfo = getCallerInfoFromITestResult(result, "FAILED");
            String throwableCallerInfoMessage = "Error occurred : " + getThrowableCallerInfoMessageFromITestResult(result, "FAILED");

            ObjectManager.getContext().test.fail(throwableCallerInfoMessage, MediaEntityBuilder.createScreenCaptureFromPath(Screenshot.capture(callerInfo)).build());

            logResultInLog(result, "FAILED");

        } catch (Exception e) {
            System.out.println("#####\nException in onTestFailure method of Listener class\n" + Arrays.toString(e.getStackTrace()) + "\n#####");
        } finally {
            ObjectManager.release();
            System.out.println("count : " + count++);}
    }

    public void onTestSkipped(ITestResult result) {
        try {
            String throwableCallerInfoMessage = "Error occurred : " + getThrowableCallerInfoMessageFromITestResult(result, "SKIPPED");

            ObjectManager.getContext().test.skip(throwableCallerInfoMessage);

            logResultInLog(result, "SKIPPED");
        } catch (Exception e) {
            System.out.println("#####\nException in onTestSkipped method of Listener class\n" + Arrays.toString(e.getStackTrace()) + "\n#####");
        } finally {
            ObjectManager.release();
            System.out.println("count : " + count++);
        }
    }

    public void onStart(ITestContext context) {
        log.info("##### Test Cases Started #####");
    }

    public void onFinish(ITestContext context) {
        log.info("##### Test Cases Ended #####");
    }

    // log the result of test case if it is FAILED or SKIPPED
    private void logResultInLog(ITestResult result, String status) {
        try {
            Throwable throwable = result.getThrowable();

            if (throwable != null) {
                for (StackTraceElement element : throwable.getStackTrace()) {
                    if (element.getClassName().equals(result.getTestClass().getName())) {
                        int lineNumber = element.getLineNumber();
                        String fileName = element.getFileName();
                        String message = fileName + " " + result.getMethod().getMethodName() + " " + lineNumber + " - " + result.getThrowable().getMessage() + " - " + status;

                        log.error(message);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logWarningInExtentReport(e, "Could not write test result log in log");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Could not write test result log in log", e, Level.WARN);
        }
    }

    private String getCallerInfoFromITestResult(ITestResult result, String status) {
        try {
            Throwable throwable = result.getThrowable();
            String className = result.getTestClass().getName();
            String methodName = result.getName();

            if (throwable != null) {
                for (StackTraceElement element : throwable.getStackTrace()) {
                    if (element.getClassName().equals(result.getTestClass().getName())) {
                        int lineNumber = element.getLineNumber();
                        return className + " " + methodName + " " + lineNumber + " - " + status;
                    }
                }
            } else {
                return className + " " + methodName + " - " + status;
            }
        } catch (Exception e) {
            logWarningInExtentReport(e, "Could not get the caller information from the ITestResult");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Could not get the caller information from the ITestResult", e, Level.WARN);
        }
        return "";
    }

    private String getThrowableCallerInfoMessageFromITestResult(ITestResult result, String status) {
        try {
            String className = result.getTestClass().getName();
            String methodName = result.getName();

            Throwable throwable = result.getThrowable();
            if (throwable != null) {
                for (StackTraceElement element : throwable.getStackTrace()) {
                    if (element.getClassName().equals(result.getTestClass().getName())) {

                        String throwableClassName = throwable.getClass().getName();
                        int lineNumber = element.getLineNumber();
                        String throwableMessage = throwable.getMessage();

                        return throwableClassName + " : " + className + " " + methodName + " " + lineNumber + " - " + throwableMessage + " - " + status;
                    }
                }
            } else {
                return className + " " + methodName + " - " + status;
            }
        } catch (Exception e) {
            logWarningInExtentReport(e, "Could not get the caller information and throwable message from the ITestResult");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Could not get the caller information and throwable message from the ITestResult", e, Level.WARN);
        }
        return "";
    }
}