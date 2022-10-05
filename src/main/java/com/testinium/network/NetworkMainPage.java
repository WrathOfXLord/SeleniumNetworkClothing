package com.testinium.network;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NetworkMainPage {
    WebDriver driver;

    @FindBy(id = "search")
    private WebElement searchBoxElement;

    @FindBy(className = "header__basketTrigger")
    private WebElement cartButton;

    @FindBy(className = "header__basketProductBtn")
    private WebElement removeItemButton;

    @FindBy(css = ".-black.o-removeCartModal__button")
    private WebElement confirmRemoveButton;

    @FindBy(className = "header__emptyBasketText")
    private WebElement emptyCartText;

    public NetworkMainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getEmptyCartText() {
        return emptyCartText.getText();
    }

    public void clickCartButton() {
        cartButton.click();
    }

    public void clickRemoveItemButton() {
        removeItemButton.click();
    }

    public void clickConfirmRemoveButton() {
        confirmRemoveButton.click();
    }

    public void setSearchBoxElement(String query) {
        searchBoxElement.sendKeys(query);
    }

    public void search() {
        searchBoxElement.sendKeys(Keys.RETURN);
    }
}
