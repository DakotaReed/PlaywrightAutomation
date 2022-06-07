package Tests.PageObjectPatternExample;

import com.microsoft.playwright.*;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.nio.file.Paths;

//mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="codegen https://www.saucedemo.com/"

// Run from cmd(Command Prompt) not power shell!!
//mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="show-trace trace.zip"

public class MainPage {

    Page page;
    BrowserContext context;
    Browser browser;
    private final String username="selenium";
    private final String password="webdriver";
    private final String occupation="QA Automation";
    private final String age="150";
    private final String location="Never Land";
    LoginPage login;
    FormPage form;
    ExitPage exit;


    @BeforeClass
    public void StartSession()   {
        Playwright playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setChannel("chrome"));
        context = browser.newContext();
        // Start tracing before creating / navigating page
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true));
        // Open new page
        page = context.newPage();
        page.navigate("http://atidcollege.co.il/Xamples/webdriveradvance.html");

        login=new LoginPage(page);
        form=new FormPage(page);
        exit=new ExitPage(page);

    }

    @Test(description = "Testing webdriver advance website")
    @Description("By using Page Objects Design Pattern Example")
    public void Test01() {

        login.sigh_in(username, password);
        form.fillForm(occupation, age, location);
        boolean isButtonDisplayed= exit.isDisplayed();
        exit.click();

        Assert.assertTrue(isButtonDisplayed);

    }


    @AfterClass
    public void CloseSession()
    {
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace1.zip")));
        context.close(); // Dispase contaxt once it is no longed needed
    }

}


