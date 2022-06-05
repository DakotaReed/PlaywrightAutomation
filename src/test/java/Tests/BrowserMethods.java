package Tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BrowserMethods {
    Page page;
    Browser browser;
    String expectedUrl="https://www.google.com/";
    String expectedTitle="Google";

    @BeforeClass
    public void StartSession() {
        Playwright playwright = Playwright.create();
        browser = playwright.chromium().
                launch(new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setChannel("chrome"));

        page = browser.newPage();
        page.navigate("https://google.com");

    }

    @Test
    public void Test01_VerifyURL() {

        String actualUrl = page.url();
        if (actualUrl.equals(expectedUrl)) {
            System.out.println("Test Passed!");
        } else {
            System.out.println("Test Failed");
            System.out.println("Actual Url is: "+actualUrl);
        }

    }

    @Test
    public void Test02_VerifyTitle() {

        String actualTitle = page.title();
        if (actualTitle.equals(expectedTitle)) {
            System.out.println("Test Passed!");
        } else {
            System.out.println("Test Failed");
            System.out.println("Actual Title is: "+actualTitle);
        }

    }

    @Test
    public void Test03_PrintDetails() {

        System.out.println("Browser version is: "+browser.version());
        System.out.println("Browser IsConnected is: "+browser.isConnected());

    }

    @AfterClass
    public void CloseSession() throws InterruptedException {
        //Thread.sleep(5000);
    }

}
