package com.testinium.network;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NetworkCartPage {
    WebDriver driver;

    @FindBy(className = "cartItem__attrValue")
    /**
     * Attributes are color and size. We need the size.
     * Odds are sizes, evens are colors.
     * We need sizes.
     */
    private List<WebElement> attributes;

    @FindBy(className = "header__logo")
    private WebElement networkLogoButton;

    @FindBy(className = "-labelPrice")
    private WebElement oldPrice;

    @FindBy(className = "-sale")
    private WebElement actualPrice;

    @FindBy(className = "continueButton")
    private WebElement continueButton;

    NetworkCartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickNetworkButton() {
        networkLogoButton.click();
    }

    public void clickContinue() {
        continueButton.click();
    }
    
    public String getOldPrice() {
        return oldPrice.getText();
    }

    public String getActualPrice() {
        return actualPrice.getText();
    }

    public String getProductSize() {
        return attributes.get(0).getText();
    }
}
