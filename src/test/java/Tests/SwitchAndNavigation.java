package Tests;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SwitchAndNavigation {
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
        page.navigate("https://atidcollege.co.il/Xamples/ex_switch_navigation.html");

    }

    @Test
    public void Test01_VerifyAlert() {

        AtomicReference<String> alertText= new AtomicReference<>("");

        page.onceDialog(dialog ->
        {
            System.out.println(dialog.message());
            alertText.set(dialog.message());
            dialog.accept();
        });

        System.out.println("Alert text is: "+alertText);

        page.locator("#btnAlert").click();
        String expectedOutput="Alert is gone.";
        Assert.assertEquals(page.locator("#output").textContent(), expectedOutput);


    }

    @Test
    public void Test02_VerifyPrompt() {

        AtomicReference<String> promptText= new AtomicReference<>("");

        page.onceDialog(dialog ->
        {
            System.out.println(dialog.message());
            promptText.set(dialog.message());
            dialog.accept("Dana"); // input to send to prompt
        });

        page.locator("#btnPrompt").click();
        System.out.println("Prompt text is: "+promptText);
        String expectedOutput="Dana";
        Assert.assertEquals(page.locator("#output").textContent(), expectedOutput);


    }

    @Test
    public void Test03_VerifyConfirmBox() {

        page.onceDialog(dialog ->
        {
            System.out.println(dialog.message());
            dialog.dismiss();
        });

        page.locator("#btnConfirm").click();
        String expectedOutput="Rejected!";
        Assert.assertEquals(page.locator("#output").textContent(), expectedOutput);


    }

    @Test
    public void Test04_VerifyIFrameContent() {

        Locator iFrameElement=page
                .frameLocator("//iframe[@src='ex_switch_newFrame.html']")
                .locator("#iframe_container");
        System.out.println("IFrame text is: "+iFrameElement.textContent());

        Assert.assertEquals(page.locator("#title").textContent(),"Switch and Navigate");


    }

    @Test
    public void Test05_VerifyTab() {

        page.locator("#btnNewTab").click();

        List<Page> myPages=browserContext.pages();
        System.out.println(myPages.size());

        Page originalPage= myPages.get(0);
        Page tabPage= myPages.get(1); // considering that there is only one tab open at this point

        System.out.println("Original Page URL is: "+originalPage.url());
        System.out.println("Tab Page URL is: "+tabPage.url());

        String tabTextContent=tabPage.locator("#new_tab_container").textContent();
        System.out.println(tabTextContent);
        tabPage.close();

        Assert.assertEquals(page.locator("#title").textContent(),"Switch and Navigate");

    }

    @Test
    public void Test06_VerifyWindow() {

        page.locator("//a[@href='ex_switch_newWindow.html']").click();
        Page newWindow=browserContext.pages().get(1);

        System.out.println("New window text is: "+newWindow.locator("#new_window_container").textContent());

        newWindow.close();

        Assert.assertEquals(page.locator("#title").textContent(),"Switch and Navigate");

    }

    @AfterClass
    public void CloseSession() throws InterruptedException {
        browserContext.close();
        //Thread.sleep(5000);
    }

}
