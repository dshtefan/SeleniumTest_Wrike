package com.wrike.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class QuestionsPage {
    private WebDriver driver;
    private List<WebElement> questions;

    public QuestionsPage(WebDriver driver) {
        this.driver = driver;
    }

    private By questionDiv = By.xpath("//div[@class='radio' or @class='survey-question-radio__wrapper']");
    private By answerButton = By.xpath(".//*[@class='survey-question-radio__button switch__button' or " +
            "@class='survey-question-radio__button' or @class='switch__button']");
    private By answerInput = By.xpath(".//*[@class='switch__input' or @class='survey-question-radio__other-input']");
    private By submitButton = By.xpath("//*[contains(text(), 'Submit results')]");
    private By successMessage = By.xpath("//*[@class='survey-success' or @class='resend-page__cell--success']");
    private By followUsSection = By.xpath("//*[@class='wg-footer__group wg-footer__group--social']");

    public List<WebElement> getQuestions(){
        questions = driver.findElements(questionDiv);
        return questions;
    }

    public List<WebElement> getAnswerList(int questionIndex) {
        return questions.get(questionIndex).findElements(answerButton);
    }

    public WebElement getAnswerOption(int questionIndex, int answerIndex) {
        return getAnswerList(questionIndex).get(answerIndex);
    }

    public QuestionsPage selectAnswer(int questionIndex, int answerIndex) {
        getAnswerOption(questionIndex, answerIndex).click();
        return this;
    }

    public boolean isEntryField(int questionIndex, int answerIndex) {
        return getAnswerOption(questionIndex, answerIndex).findElements(answerInput).size() > 0;
    }

    public WebElement getEntryField(int questionIndex, int answerIndex) {
        return getAnswerOption(questionIndex, answerIndex).findElement(answerInput);
    }

    public QuestionsPage fillEntryField(int questionIndex, int answerIndex, String text) {
        getEntryField(questionIndex, answerIndex).sendKeys(text);
        return this;
    }

    public QuestionsPage clickSubmit() {
        driver.findElement(submitButton).click();
        return this;
    }

    public WebElement getSuccessMessage() {
        return driver.findElement(successMessage);
    }

    public WebElement getFollowUsSection() {
        return driver.findElement(followUsSection);
    }
}
