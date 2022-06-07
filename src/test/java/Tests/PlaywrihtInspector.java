package Tests;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="codegen https://www.saucedemo.com/"

public class PlaywrihtInspector {

    Page page;
    BrowserContext context;
    Browser browser;


    @BeforeClass
    public void StartSession()   {
        Playwright playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setChannel("chrome"));
        context = browser.newContext();
        // Open new page
        page = context.newPage();
        // Go to https://www.saucedemo.com/
        page.navigate("https://www.saucedemo.com/");

    }

    @Test
    public void Test01() {


        // Fill [data-test="username"]
        page.locator("[data-test=\"username\"]").fill("standard_user");
        // Fill [data-test="password"]
        page.locator("[data-test=\"password\"]").fill("secret_sauce");
        // Click [data-test="login-button"]
        page.locator("[data-test=\"login-button\"]").click();
        // assertThat(page).hasURL("https://www.saucedemo.com/inventory.html");
        // Click text=Open Menu
        page.locator("text=Open Menu").click();
        // Click text=Logout
        page.locator("text=Logout").click();
        // assertThat(page).hasURL("https://www.saucedemo.com/");
        Assert.assertEquals(page.url(),"https://www.saucedemo.com/");
    }
}


