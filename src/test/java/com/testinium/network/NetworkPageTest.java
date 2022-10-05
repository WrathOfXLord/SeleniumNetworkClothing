package com.testinium.network;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Unit test for simple App.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NetworkPageTest extends TestBase{
    NetworkMainPage mainPage;
    NetworkResultPage resultPage;
    NetworkCartPage cartPage;
    NetworkLoginPage loginPage;
    public static String selectedProductSize, selectedProductPrice;

    @Test
    @Order(1)
    public void mainPageTest() {
        NetworkPageTest.logger.info("Test 1: Mainpage.");
        mainPage = new NetworkMainPage(driver);
        // verifying that the given url and current url are the same
        assertEquals(driver.getCurrentUrl(), TestBase.mainPageURL);
        NetworkPageTest.logger.info("Assertion Passed: URLs are the same.");
        // log atilacak bolge
        mainPage.setSearchBoxElement("ceket");
        mainPage.search();
    }

    @Test
    @Order(2)
    public void resultsPageTest() throws InterruptedException {
        resultPage = new NetworkResultPage(driver);
        NetworkPageTest.logger.info("Test2: Results page.");
        Thread.sleep(longWait);
        resultPage.gotoShowMore();

        // saving current url before clicking the show more button
        String urlBeforeClick = driver.getCurrentUrl();
        NetworkPageTest.logger.info("Clicking to show more.");
        Thread.sleep(shortWait);
        resultPage.clickShowMore();
        Thread.sleep(longWait);
        // checking the urls
        assertNotEquals(urlBeforeClick, driver.getCurrentUrl());
        NetworkPageTest.logger.info("Assertion Passed: URLs are different after clicking show more button.");

        Thread.sleep(longWait);
        NetworkPageTest.logger.info("Selecting the first discounted product.");
        resultPage.gotoDiscountedProduct();
        Thread.sleep(longWait);
        NetworkPageTest.logger.info("Selecting the size.");
        resultPage.selectSize();
        Thread.sleep(shortWait);
        NetworkPageTest.logger.info("Redirecting to cart page.");
        resultPage.gotoCart();

        selectedProductSize = resultPage.getSelectedSize();
        selectedProductPrice = resultPage.getSelectedProductPrice();
        Thread.sleep(longWait);
    }

    @Test
    @Order(3)
    public void cartPageTest() throws InterruptedException {
        cartPage = new NetworkCartPage(driver);
        Thread.sleep(longWait);
        NetworkPageTest.logger.info("Test 3: Shopping cart page.");
        assertEquals(cartPage.getProductSize(), NetworkPageTest.selectedProductSize);
        NetworkPageTest.logger.info("Assertion Passed: Product sizes are the same.");
        
        assertEquals(cartPage.getActualPrice(), NetworkPageTest.selectedProductPrice);
        NetworkPageTest.logger.info("Assertion Passed: Product prices are the same.");

        // no need to convert to integer. if it is lexicographically greater, then it is also numerically greater
        // but lexicographical difference doesn't equal to numerical difference
        // no need to check how greater or lesser
        NetworkPageTest.logger.info("Verifying that discounted price is lesser than the normal price.");
        int priceDifference = cartPage.getActualPrice().compareTo(cartPage.getOldPrice());
        NetworkPageTest.logger.info("difference: " + priceDifference);
        assertTrue(priceDifference < 0);
        NetworkPageTest.logger.info("Assertion Passed: Discounted price is lesser than the normal price.");
        cartPage.clickContinue();
        NetworkPageTest.logger.info("Continue button clicked.");
        Thread.sleep(longWait);
        NetworkPageTest.logger.info("Redirecting to login page.");
    }

    @Test
    @Order(4)
    public void loginPageTest() throws InterruptedException, FileNotFoundException {

        loginPage = new NetworkLoginPage(driver);
        NetworkPageTest.logger.info("Test 4: Login page.");
        Thread.sleep(longWait);
        
        NetworkPageTest.logger.info("Extracting login info from csv file.");
        loginPage.extractCSVWithoutHeader("./data.csv", ",", 0);
        Thread.sleep(shortWait);
        NetworkPageTest.logger.info("Filling email and password inputs.");
        loginPage.fillEmail();
        Thread.sleep(shortWait);
        loginPage.fillPassword();
        Thread.sleep(shortWait);
        String loginButtonText = loginPage.getLoginButton();
        assertEquals(loginButtonText.trim(), "GİRİŞ YAP");
        NetworkPageTest.logger.info("Assertion Passed: Login button exists.");
        loginPage.clickLogin();
        NetworkPageTest.logger.info("Logging in.");
        Thread.sleep(longWait);
    }

    @Test
    @Order(5)
    public void backToCartPageTest() throws InterruptedException {
        cartPage = new NetworkCartPage(driver);
        NetworkPageTest.logger.info("Test 5: Back to shopping cart page after login.");
        NetworkPageTest.logger.info("Redirecting to mainpage via network button.");
        cartPage.clickNetworkButton();
        Thread.sleep(longWait);
    }

    @Test
    @Order(6)
    public void backToMainPageTest() throws InterruptedException {
        mainPage = new NetworkMainPage(driver);
        NetworkPageTest.logger.info("Test6: Back to mainpage.");
        NetworkPageTest.logger.info("Opening the shopping cart.");
        Thread.sleep(shortWait);
        mainPage.clickCartButton();
        Thread.sleep(shortWait);
        NetworkPageTest.logger.info("Removing the product.");
        mainPage.clickRemoveItemButton();
        Thread.sleep(shortWait);
        mainPage.clickConfirmRemoveButton();
        Thread.sleep(longWait);
        mainPage.clickCartButton();
        NetworkPageTest.logger.info("Verifying the shopping cart is empty.");
        Thread.sleep(shortWait);
        assertEquals(mainPage.getEmptyCartText().trim(), "Sepetiniz Henüz Boş");
        NetworkPageTest.logger.info("Assertion Passed: The Shopping cart is empty.");
        Thread.sleep(longWait);
    }

}
