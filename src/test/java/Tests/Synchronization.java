package Tests;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Synchronization {
    Page page;
    Browser browser;
    BrowserContext browserContext;

    @BeforeClass
    public void StartSession() {
        Playwright playwright = Playwright.create();
        browser = playwright.chromium().
                launch(new BrowserType.LaunchOptions()
                        .setSlowMo(600) //in milliseconds
                        .setHeadless(false)
                        .setChannel("chrome"));
        browserContext=browser.newContext(); // will use it to handle windows and tabs
        page = browserContext.newPage();
        page.navigate("https://atidcollege.co.il/Xamples/ex_synchronization.html");

    }

    @Test
    public void Test01_WaitForOptions() {

        page.locator("#btn").click();
        page.locator("#message").waitFor(new Locator.WaitForOptions().setTimeout(3000));

        Assert.assertTrue(page.locator("#message").isVisible());

    }

    @Test
    public void Test02_Sleep() throws InterruptedException {

        page.locator("#hidden").click();
        Thread.sleep(5000);
        Locator elem=page.locator("#loading1");

        System.out.println(elem.textContent());

    }

    @Test
    public void Test03_WaitForSelector() {

        page.locator("#rendered").click();
        page.waitForSelector("#finish2", new Page.WaitForSelectorOptions().setTimeout(2000));
        String output=page.locator("#finish2").textContent();

        System.out.println(output);

        Assert.assertEquals(output, "My Rendered Element After Fact!");

    }

    @AfterClass
    public void CloseSession() throws InterruptedException {
        browserContext.close();
        //Thread.sleep(5000);
    }

}
