package Tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.SelectOption;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Controllers {
    Page page;
    Browser browser;
    String myFirstName="Dakota";
    String myLastName="Reed";


    @BeforeClass
    public void StartSession() {
        Playwright playwright = Playwright.create();
        browser = playwright.chromium().
                launch(new BrowserType.LaunchOptions()
                        .setSlowMo(800) //in milliseconds
                        .setHeadless(false)
                        .setChannel("chrome"));
        page = browser.newPage();
        page.navigate("https://atidcollege.co.il/Xamples/ex_controllers.html");

    }

    @Test
    public void Test01_VerifyURLContent() {

        //Update text
        page.locator("[name=firstname]").fill(myFirstName);
        page.locator("[name=lastname]").fill(myLastName);
        //Check checkbox
        page.check("[id='exp-3']");

        //Select from select tag
        String continentsSelector="#continents";
        //Select By Visible Text aka By Label
        page.selectOption(continentsSelector, new SelectOption().setLabel("Europe"));
        //Select By Index
        page.selectOption(continentsSelector, new SelectOption().setIndex(2));
        //Select By Value
        page.selectOption(continentsSelector, new SelectOption().setValue("austria"));

        page.locator("#submit").click();

        String sUrl=page.url();

        if(sUrl.contains(myFirstName) && sUrl.contains(myLastName))
            System.out.println("Test Passed!");
        else
            System.out.println("Test Failed!");

    }

    @AfterClass
    public void CloseSession() throws InterruptedException {
        //Thread.sleep(5000);
    }

}
