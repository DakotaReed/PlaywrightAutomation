package Tests.PageObjectPatternExample;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class ExitPage {

    private Page page;
    private Locator btn_submit;

    public ExitPage(Page page)
    {
        this.page=page;
        btn_submit= page.locator("button[type='button']");
    }

    @Step("Clicking on the submit button")
    public void click()
    {
        btn_submit.click();
    }

    @Step("Check if the button is displayed")
    public boolean isDisplayed()
    {
        return btn_submit.isVisible();
    }

}
