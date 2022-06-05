package Tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.SelectOption;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class AssertsAndVerifications {
    Page page;
    Browser browser;
    String myHeight="180";
    String myWeight="95";
    SoftAssert soft=new SoftAssert();

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

    @Test
    public void Test01_VerifyResult() {

        page.locator("#weight").fill(myWeight);
        page.locator("#hight").fill(myHeight);
        page.locator("#calculate_data").click();
        String expectedResult="29";
        String actualResult=page.locator("#bmi_result").inputValue();
        System.out.println(actualResult);
        Assert.assertEquals(actualResult,expectedResult);

    }

    @Test
    public void Test02_VerifyCalcButton() {

        Locator myButton= page.locator("#calculate_data");

       double width = myButton.boundingBox().width;
       double height = myButton.boundingBox().height;
       double xCoordinate = myButton.boundingBox().x;
       double yCoordinate = myButton.boundingBox().y;

       System.out.println("Button Width: "+width);
       System.out.println("Button Height: "+height);
       System.out.println("Button X: "+xCoordinate);
       System.out.println("Button Y: "+yCoordinate);

        soft.assertEquals(Math.round(width),133);
        soft.assertEquals(Math.round(height), 35);
        soft.assertEquals(Math.round(xCoordinate), 396);
        soft.assertEquals(Math.round(yCoordinate), 266);

        soft.assertTrue(myButton.isVisible());
        soft.assertTrue(myButton.isEnabled());
        soft.assertEquals(myButton.inputValue(), "Calculate BMI");
        soft.assertAll();

    }

    @AfterClass
    public void CloseSession() throws InterruptedException {
        //Thread.sleep(5000);
    }

}
