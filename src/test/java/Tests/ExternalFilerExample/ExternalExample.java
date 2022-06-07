package Tests.ExternalFilerExample;

import com.microsoft.playwright.*;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Paths;

import static org.testng.Assert.fail;

public class ExternalExample {
    Page page;
    Browser browser;
    BrowserContext browserContext;


    @BeforeClass
    public void StartSession() {
        Playwright playwright = Playwright.create();
        browser = playwright.chromium().
                launch(new BrowserType.LaunchOptions()
                        .setSlowMo(800) //in milliseconds
                        .setHeadless(false)
                        .setChannel("chrome"));
        browserContext=browser.newContext();
        // Start tracing before creating / navigating page
        browserContext.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true));
        page = browserContext.newPage();
        page.navigate(getData("URL"));

    }

    @Test(description = "Test BMI Results")
    @Description("Using my data to verify a bmi result")
    public void Test01_VerifyResult() {

        try {
            update_text(page.locator("#weight"), getData("WEIGHT"));
            update_text(page.locator("#hight"), getData("HEIGHT"));
            click(page.locator("#calculate_data"));
            String actualResult = getAttribute(page.locator("#bmi_result"));
            System.out.println(actualResult);
            verifyEquals(actualResult, getData("EXPECTED_BMI"));
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
    public void CloseSession()
    {
        browserContext.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace2.zip")));
        browserContext.close(); // Dispose context once it is no longed needed
    }

    public String getData(String nodeName) {
        DocumentBuilder dBuilder;
        Document doc = null;
        File fXmlFile = new File("C:\\Users\\trollbot\\Desktop\\1.1\\PlaywrightAutomation\\src\\test\\java\\Tests\\ExternalFilerExample\\testConfig.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = (Document) dBuilder.parse(fXmlFile);
            }
        catch (Exception e) {
            System.out.println("Exception in reading XML file: "+ e);
        }
        //doc.getDocumentElement().normalize();

        doc.getDocumentElement().normalize();


        //return ((org.w3c.dom.Document)doc).getElementsByTagName(nodeName).item(0).getTextContent();
        return doc.getElementsByTagName(nodeName).item(0).getTextContent();
    }



}
