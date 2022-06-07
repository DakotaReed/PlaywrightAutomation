package Tests;

import com.microsoft.playwright.*;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

public class ReportingSystem {
    Page page;
    Browser browser;
    String myHeight = "180";
    String myWeight = "95";

    @BeforeClass
    public void StartSession() {
        Playwright playwright = Playwright.create();
        browser = playwright.chromium().
                launch(new BrowserType.LaunchOptions()
                        .setSlowMo(800) //in milliseconds
                        .setHeadless(false)
                        .setChannel("chrome"));
        page = browser.newPage();
        page.navigate("https://atidcollege.co.il/Xamples/bmi/");

    }

    @Test(description = "Test BMI Results")
    @Description("Using my data to verify a bmi result")
    public void Test01_VerifyResult() {

        try {
            update_text(page.locator("#weight"), myWeight);
            update_text(page.locator("#hight"), myHeight);
            click(page.locator("#calculate_data"));
            String expectedResult = "29";
            String actualResult = getAttribute(page.locator("#bmi_result"));
            System.out.println(actualResult);
            verifyEquals(actualResult, expectedResult);
        } catch (Exception | AssertionError e) {
            saveScreenShot();
            fail("Test Failed: ", e);
        }

    }

    @Step("Update Text Field")
    public void update_text(Locator elem, String value) {
        elem.fill(value);
    }

    @Step("Click on Element")
    public void click(Locator elem) {
        elem.click();
    }

    @Step("Get Text From Input Element")
    public String getAttribute(Locator elem) {
        return elem.inputValue();
    }

    @Step("Verify Results")
    public void verifyEquals(String actual, String expected) {
        Assert.assertEquals(actual, expected);
    }

    @Attachment(value = "Page Screen-Shot", type = "image/png")
    public byte[] saveScreenShot() {
        return page.screenshot();
    }


    @AfterClass
    public void CloseSession() throws InterruptedException {
        //Thread.sleep(5000);
    }

}
