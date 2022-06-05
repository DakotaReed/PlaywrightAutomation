package Tests;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Actions {
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
        page.navigate("https://atidcollege.co.il/Xamples/ex_actions.html");

    }

    @Test
    public void Test01_DragAndDrop() {

        page.dragAndDrop("#draggable", "#droppable", new Page.DragAndDropOptions().setForce(true));

        //Custom Drag and Drop
        //Locator src=page.locator("#draggable");
        //Locator dst=page.locator("#droppable");
        //page.mouse().move(src.boundingBox().x, src.boundingBox().y);
        //page.mouse().down();
        //page.mouse().move(dst.boundingBox().x, dst.boundingBox().y);
        //page.mouse().down();

        Assert.assertEquals(page.locator("#droppable").innerText(), "Dropped!");

    }

    @Test
    public void Test02_DoubleClick()
    {
        page.locator("#dbl_click").dblclick();

        Assert.assertEquals(page.locator("#demo").innerText(), "Hello World");
    }

    @Test
    public void Test03_MouseHover()
    {
        Locator element=page.locator("#mouse_hover");
        page.mouse().move(element.boundingBox().x, element.boundingBox().y);
        //Assert.assertTrue(page.locator("span[style='background-color: rgb(255, 255, 0);']").isVisible());

        Assert.assertEquals(page.locator("#mouse_hover").getAttribute("style"), "background-color: rgb(255, 255, 0);");
    }

    @Test
    public void Test04_Scroll()
    {
        Locator elem=page.locator("#scrolled_element");
        elem.scrollIntoViewIfNeeded();
        page.mouse().move(elem.boundingBox().x, elem.boundingBox().y);

        Assert.assertEquals(elem.innerText(), "This Element is Shown When Scrolled");

    }

    @AfterClass
    public void CloseSession() throws InterruptedException {
        browserContext.close();
        //Thread.sleep(5000);
    }

}
