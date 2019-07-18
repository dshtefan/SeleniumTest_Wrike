import com.wrike.pages.MainPage;
import com.wrike.pages.QuestionsPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

public class MainTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private Random random = new Random();

    private void explicitWait(String xpath) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    private String getRandonString(int length){
        int leftLimit = (int)'a';
        int rightLimit = (int)'z';
        StringBuilder builder = new StringBuilder(length);
        for(int i = 0; i < length; i++) {
            int randomChar = leftLimit + (int)(random.nextFloat() * (rightLimit - leftLimit + 1));
            builder.append((char) randomChar);
        }
        return builder.toString();
    }

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/home/denis/IdeaProjects/ChromeDriver/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 5);
    }

    @Test
    public void myTest() {
        //1
        driver.get("https://www.wrike.com/");
        MainPage mainPage = new MainPage(driver);
        //2
        mainPage.clickGetStarted();
        //3
        mainPage.fillEmail("aaaaaaaaaaaaaaa" + "+wpt@wriketask.qaa");
        //4
        QuestionsPage questionsPage = mainPage.createAccount();
        explicitWait("//*[contains(text(), 'Submit results')]");
        //5
        List<WebElement> questions = questionsPage.getQuestions();
        for(int i = 0; i < questions.size(); i++) {
            List<WebElement> answersList = questionsPage.getAnswerList(i);
            System.out.println(answersList.size());
            int n = random.nextInt(answersList.size());
            questionsPage.selectAnswer(i, n);
            if(questionsPage.isEntryField(i, n)) {
                questionsPage.fillEntryField(i, n, getRandonString(10));
            }
        }
        questionsPage.clickSubmit();
        wait.until(ExpectedConditions.visibilityOf(questionsPage.getSuccessMessage()));
    }

    @After
    public void tearDown() {
//        driver.quit();
    }

}
