package com.testinium.network;

import java.time.Duration;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

// to import the appropriate chromedriver to automate 
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
    // Chromium based browser
    public static final String browserPath = "C:/Program Files/BraveSoftware/Brave-Browser/Application/brave.exe";
    public static String mainPageURL = "https://www.network.com.tr/";
    public static String chromiumVersion = "106.0.5249.91";
    public static WebDriver driver = null;
    protected static Logger logger = LogManager.getLogger(TestBase.class);

    /**
     * ms
     */
    final long shortWait = 1000, longWait = 2000; 

    @BeforeAll
    public static void initialize() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        // options needed if we use a different browser
        ChromeOptions options = new ChromeOptions();
        options.setBinary(TestBase.browserPath);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Thread.sleep(2000);
        driver.get(mainPageURL);
        logger.info("Connected to browser.");
    }

    @AfterAll
    public static void terminate() throws InterruptedException {
        Thread.sleep(2000);
        logger.info("Connection closed.");
        TestBase.driver.quit();
    }
}
