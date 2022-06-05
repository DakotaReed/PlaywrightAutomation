package Tests;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LocatorsAdvanced {
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
        page.navigate("https://atidcollege.co.il/Xamples/ex_locators.html");

    }

    @Test
    public void Test01_VerifyElements() {

        Locator locatorByID= page.locator("[id='locator_id']");
        Locator locatorByName= page.locator("[name='locator_name']");
        Locator locatorByText= page.locator("text='Find my locator (3)'");
        Locator locatorByClass= page.locator(".locator_class");
        Locator locatorByFirsta= page.locator("a").nth(1);
        Locator locatorBySeconda= page.locator("a").nth(2);
        Locator locatorByCssSelector= page.locator("input[myname='selenium']");
        Locator locatorByXPath= page.locator("//*[@id='contact_info_left']/button");

        System.out.println("Element Value: "+locatorByID.textContent());
        System.out.println("Element Value: "+locatorByName.textContent());
        System.out.println("Element Value: "+locatorByText.textContent());
        System.out.println("Element Value: "+locatorByClass.textContent());
        System.out.println("Element Value: "+locatorByFirsta.textContent());
        System.out.println("Element Value: "+locatorBySeconda.textContent());
        System.out.println("Element Value: "+locatorByCssSelector.getAttribute("value"));
        System.out.println("Element Value: "+locatorByXPath.textContent());

    }

    @AfterClass
    public void CloseSession() throws InterruptedException {
        //Thread.sleep(5000);
    }

}
