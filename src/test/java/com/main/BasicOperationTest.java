package com.main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;
import org.testng.Assert;
import org.testng.annotations.*;

//@Test()
public class BasicOperationTest {

    private WebDriver driver;

    @Test(groups = {"BasicOperationTest.init"})
    public void init() {
//        System.out.println("init driver");
//        System.setProperty("webdriver.chrome.driver", "D:\\Doc--GitRepo\\Java\\test\\src\\main\\resources\\chromedriver.exe");
//        driver = new ChromeDriver();
        driver = ChromeDriverSingleton.getInstance();
    }

//    @Test(groups = {"BasicOperationTest.destroy"}, dependsOnGroups = {"EnterpriseManagement"})
//    public void destroy() {
//        System.out.println("destroy driver");
//        driver.close();
//    }

    @Test(groups = {"BasicOperation.logIn"},dependsOnGroups = {"BasicOperationTest.init"})
    @Parameters({"account","password"})
    public void logIn(String account,String password) {
        driver.get("https://192.168.0.100:3999/login");
        ((ChromeDriver) driver).findElementByXPath("//*[@id=\"login-container\"]/div/div[2]/div[1]/form/div[1]/div/div/input").sendKeys(account);
        ((ChromeDriver) driver).findElementByXPath("//*[@id=\"login-container\"]/div/div[2]/div[1]/form/div[2]/div/div/input").sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"login-container\"]/div/div[2]/div[1]/form/div[4]/div/button")).click();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        String text = driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[1]/div/div/div[2]/div[1]")).getText();
        Assert.assertEquals(text, "唐锐");
//        driver.close();
    }




}