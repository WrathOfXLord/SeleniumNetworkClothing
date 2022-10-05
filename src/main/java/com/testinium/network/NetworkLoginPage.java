package com.testinium.network;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NetworkLoginPage {
    WebDriver driver;
    private String email, password;

    @FindBy(css = ".input.js-trim")
    private WebElement emailInput;

    @FindBy(id = "n-input-password")
    private WebElement passwordInput;

    @FindBy(xpath = "//*[@id='login']/button")
    private WebElement loginButton;

    public void clickLogin() {
        loginButton.click();
    }

    public String getLoginButton() {
        return loginButton.getText();
    }

    public void fillEmail() {
        emailInput.sendKeys(email);
    }
    
    public void fillPassword() {
        passwordInput.sendKeys(password);
    }

    public NetworkLoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }



    // Works correctly if the csv file has only one line
    public void extractCSVWithoutHeader(String filePath, String delimiter, int lineIndex) throws FileNotFoundException {
        Scanner file = new Scanner(new File(filePath));

        String line = null;
        List<String[]> csvMap = new ArrayList<>();
        while(file.hasNextLine()){
            line = file.nextLine();
            csvMap.add(line.trim().split(delimiter));
        }

        file.close();

        email = csvMap.get(lineIndex)[0].trim();
        password = csvMap.get(lineIndex)[1].trim();
    }
}
