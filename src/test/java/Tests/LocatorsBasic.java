package Tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LocatorsBasic {
    Page page;
    Browser browser;

    @BeforeClass
    public void StartSession() {
        Playwright playwright = Playwright.create();
        browser = playwright.chromium().
                launch(new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setChannel("chrome"));
        page = browser.newPage();
        page.navigate("https://www.saucedemo.com/");

    }

    @Test
    public void Test01_VerifyElements() {

        //Attribute by ID
        page.locator("[id='user-name']");
        //same as
        page.locator("#user-name");
        //Attribute by ID
        page.locator("#password");
        //Text Selector
        page.locator("text=LOGIN").click();


    }

    @AfterClass
    public void CloseSession() throws InterruptedException {
        //Thread.sleep(5000);
    }

}
