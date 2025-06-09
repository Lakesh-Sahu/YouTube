package utility;

import org.apache.logging.log4j.Level;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.Semaphore;

public class ObjectManager extends Base {
    private static final ThreadLocal<ObjectCreator> context = new ThreadLocal<>();
    private static final Semaphore semaphore = new Semaphore(maxThreadAllowed, isFair);

    public static void init(WebDriver driver, String className, String methodName, String description) {
        context.set(new ObjectCreator(driver, className, methodName, description));
    }

    public static ObjectCreator getContext() {
        return context.get();
    }

    public static void remove() {
        context.remove();
    }

    public static void acquire() {
        try {
            semaphore.acquire();
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while acquiring thread on ObjectManager");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while acquiring thread on ObjectManager", e, Level.WARN);
        }
    }

    public static void release() {
        try {
            semaphore.release();
        } catch (Exception e) {
            logWarningInExtentReport(e, "Exception while releasing thread on ObjectManager");
            logExceptionInLog(getCallerInfoFromStackTrace(Thread.currentThread().getStackTrace()), "Exception while releasing thread on ObjectManager", e, Level.WARN);
        }
    }
}