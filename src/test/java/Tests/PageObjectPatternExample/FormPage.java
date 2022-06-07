package Tests.PageObjectPatternExample;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class FormPage {

    private Page page;
    private Locator txt_occupation;
    private Locator txt_age;
    private Locator txt_location;
    private Locator btn_submit;

    public FormPage(Page page)
    {
       this.page=page;
       this.txt_occupation=page.locator("#occupation");
       this.txt_age=page.locator("#age");
       this.txt_location=page.locator("#location");
       this.btn_submit=page.locator("//input[@type='button']");
    }

    @Step("Fill the form details")
    public void fillForm(String occupation,String age,String location)
    {
        this.txt_occupation.fill(occupation);
        this.txt_age.fill(age);
        this.txt_location.fill(location);
        this.btn_submit.click();
    }

}
