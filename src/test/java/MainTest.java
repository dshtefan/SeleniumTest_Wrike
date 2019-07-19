import com.wrike.pages.MainPage;
import com.wrike.pages.QuestionsPage;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Assert;
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
        MainPage mainPage = step1();
        //2
        step2(mainPage);
        //3
        step3(mainPage);
        //4
        QuestionsPage questionsPage = step4(mainPage);
        //5
        step5(questionsPage);
        //6
        step6(questionsPage);
    }

    @Step
    public MainPage step1() {
        driver.get("https://www.wrike.com/");
        return new MainPage(driver);
    }

    @Step
    public MainPage step2(MainPage page) {
        page.clickGetStarted();
        return page;
    }

    @Step
    public MainPage step3(MainPage page) {
        page.fillEmail(getRandonString(10) + "+wpt@wriketask.qaa");
        return page;
    }

    @Step
    public QuestionsPage step4(MainPage page) {
        QuestionsPage questionsPage = page.createAccount();
        explicitWait("//*[contains(text(), 'Submit results')]");
        Assert.assertNotEquals("https://www.wrike.com/", driver.getCurrentUrl());
        return questionsPage;
    }

    @Step
    public QuestionsPage step5(QuestionsPage page) {
        List<WebElement> questions = page.getQuestions();
        for(int i = 0; i < questions.size(); i++) {
            List<WebElement> answersList = page.getAnswerList(i);
            int n = random.nextInt(answersList.size());
            page.selectAnswer(i, n);
            if(page.isEntryField(i, n))
                page.fillEntryField(i, n, getRandonString(12));
        }
        page.clickSubmit();
        wait.until(ExpectedConditions.visibilityOf(page.getSuccessMessage()));
        Assert.assertTrue(page.getSuccessMessage().isDisplayed());
        return page;
    }

    @Step
    public QuestionsPage step6(QuestionsPage page) {
        WebElement followUs = page.getFollowUsSection();
        Assert.assertTrue(
                followUs.findElements(By.xpath(".//*[@href='https://twitter.com/wrike']")).size() > 0
        );
        Assert.assertTrue(
                followUs.findElements(By.xpath("//*[@href='https://twitter.com/wrike']" +
                        "//*[local-name() = 'use' and " +
                        "@*='/content/themes/wrike/dist/img/sprite/vector/footer-icons.symbol.svg?v2#twitter']")).size() > 0
        );
        return page;
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
