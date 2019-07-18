import com.wrike.pages.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class MainTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/home/denis/IdeaProjects/ChromeDriver/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void myTest() {
        driver.get("https://www.wrike.com/");
        MainPage mainPage = new MainPage(driver);
        mainPage.createAccount("weqeqweqwe@wqdw.ru");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
