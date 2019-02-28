package ru.ke.tests;

import org.junit.Test;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogTest {
    private static WebDriver driver;

    @BeforeClass
    public static void main(){
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //userLogin();

    }
    @Test
    public void test1() {

        driver.get("http://kazak-edinstvo.ru/wp-admin");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("user_login")));
        WebElement loginField = driver.findElement(By.id("user_login"));
        loginField.sendKeys("laodik");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user_login")));
        WebElement passwordField = driver.findElement(By.id("user_pass"));
        passwordField.sendKeys("passw");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("user_login")));
        WebElement loginButton = driver.findElement(By.id("wp-submit"));
        loginButton.click();
        WebElement OpenSubMenuButton = driver.findElement(By.id("wp-admin-bar-top-secondary"));



        Actions builder = new Actions(driver);
        builder.moveToElement(OpenSubMenuButton).perform();

        By locator = By.xpath("//*[@id='wp-admin-bar-user-info']/a/span[2]");

        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        WebElement NameCheck = driver.findElement(locator);
        String UserName = NameCheck.getAttribute("textContent");
        System.out.printf("Login %s\n",UserName);
        System.out.printf("Login"+NameCheck);
        Assert.assertEquals("laodik", UserName);



        WebElement logoutButton = driver.findElement(By.id("wp-admin-bar-logout"));
        logoutButton.click();
    }

    @Test
    public void test2() {
        //driver = new ChromeDriver();
        //driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://kazak-edinstvo.ru/wp-admin");
        WebElement loginField = driver.findElement(By.id("user_login"));
        loginField.sendKeys("laodik");
        WebElement passwordField = driver.findElement(By.id("user_pass"));
        passwordField.sendKeys("passw");
        WebElement loginButton = driver.findElement(By.id("wp-submit"));
        loginButton.click();
        driver.get("http://kazak-edinstvo.ru/");
        Assert.assertTrue(driver.findElement(By.id("wpadminbar")).isDisplayed());

        WebElement OpenSubMenuButton = driver.findElement(By.id("wp-admin-bar-top-secondary"));
        Actions builder = new Actions(driver);
        builder.moveToElement(OpenSubMenuButton).perform();
        WebElement logoutButton = driver.findElement(By.id("wp-admin-bar-logout"));
        logoutButton.click();
    }

    @AfterClass
    public static void checkName () {

        driver.quit();
    }



}
