package Tests.PageObjectPatternExample;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class LoginPage {

    private Page page;
    private Locator txt_username;
    private Locator txt_password;
    private Locator btn_submit;

    public LoginPage(Page page)
    {
        this.page=page;
        this.txt_username=page.locator("[name='username2']");
        this.txt_password= page.locator("[name='password2']");
        this.btn_submit=page.locator("#submit");
    }

    @Step("Login using provided username and password")
    public void sigh_in(String username,String password)
    {
        txt_username.fill(username);
        txt_password.fill(password);
        btn_submit.click();
    }

}
