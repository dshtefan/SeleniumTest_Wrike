package com.wrike.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {
    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    private By getStartedButton = By.xpath("//div[@class='wg-header__grid']//button");
    private By emailField = By.xpath("//label/input");
    private By createAccButton = By.xpath("//button[text()='Create my Wrike account']");

    public MainPage fillEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
        return this;
    }

    public QuestionsPage createAccount() {
        driver.findElement(createAccButton).click();
        return new QuestionsPage(driver);
    }
    public MainPage clickGetStarted() {
        driver.findElement(getStartedButton).click();
        return this;
    }




}
