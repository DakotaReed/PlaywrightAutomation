package Tests;

import org.testng.annotations.*;

public class TestNGExample {

    @BeforeClass
    public void StartSession()
    {
        System.out.println("This will run before all tests");
    }

    @BeforeMethod
    public void BeforeMethod()
    {
        System.out.println("This will run before each test");
    }

    @AfterMethod
    public void AfterMethod()
    {
        System.out.println("This will run after each test");
    }

    @Test
    public void Test01()
    {
        System.out.println("This is Test01");
    }

    @Test
    public void Test02()
    {
        System.out.println("This is Test02");
    }

    @AfterClass
    public void CloserSession()
    {
        System.out.println("This will run after all tests");
    }

}
