package com.testinium.network;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NetworkResultPage {
    WebDriver driver;
    private Actions actions;
    private String selectedSize;
    private String selectedProductPrice;

    @FindBy(xpath = "//*[@id='pagedListContainer']/div[2]/div[2]/button")
    private WebElement showMoreButton;

    @FindBy(xpath="//*[contains(@id, 'product-')]/div/div[1]")
    // discounted products found by using className = "product__discountPercent"
    private List<WebElement> discountedProducts;

    @FindBy(xpath = "//*[contains(@id, 'product-')]/div/div[1]/div/div/div/div")
    private List<WebElement> productSizeList;

    @FindBy(className = "header__basket--checkout")
    private WebElement gotoCartButton;

    public NetworkResultPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getSelectedProductPrice() {
        return selectedProductPrice;
    }

    public String getSelectedSize() {
        return selectedSize;
    }

    public void gotoShowMore() {
        actions = new Actions(driver);
        actions.moveToElement(showMoreButton);
        actions.perform();
    }

    public void clickShowMore() {
        showMoreButton.click();
    }

    public List<WebElement> getDiscountedProducts() {
        return discountedProducts;
    }

    public WebElement getFirstDiscountedProduct() {
        return discountedProducts.get(0);
    }

    public void gotoDiscountedProduct() {
        WebElement firstDiscountedProduct = getFirstDiscountedProduct();
        actions = new Actions(driver);
        actions.moveToElement(firstDiscountedProduct);
        actions.perform();
        WebElement price = firstDiscountedProduct.findElement(By.xpath("//*[contains(@id, 'product-')]/div/div[2]/div/div[2]/div/span[2]"));
        selectedProductPrice = price.getText();
    }   
    
    public void selectSize() throws InterruptedException {
        int size = 0;
        for(WebElement e:productSizeList) {
            if(e.getText() == "")
                break;
            ++size;
        }
        Thread.sleep(500);
        Random r = new Random();

        WebElement randomSize = null;
        WebElement soldOut = null;
        int rand = 0;

        // Selects another size if sold out
        do {
            rand = r.nextInt(size);
            randomSize = productSizeList.get(rand);
            soldOut = randomSize.findElement(By.xpath("//*[contains(@id, 'product-')]/div/div[1]/div/div/div/div[" + (rand + 1) +"]/div/span"));
            Thread.sleep(500);
            randomSize.click();
            // Log
        } while(soldOut.getText() != "");
        selectedSize = randomSize.getText();
    }

    public void gotoCart() {
        gotoCartButton.click();
    }

}