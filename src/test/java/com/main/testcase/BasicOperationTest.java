package com.main.testcase;

import com.main.uienum.UIPathBasicEnum;
import com.main.driver.ChromeDriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;
import org.testng.Assert;
import org.testng.annotations.*;

@Test()
public class BasicOperationTest {

    private WebDriver driver;

    @Test(groups = {"BasicOperationTest.init"})
    public void init() {
//        System.out.println("init driver");
        driver = ChromeDriverSingleton.getInstance();
    }

//    @Test(groups = {"BasicOperationTest.destroy"}, dependsOnGroups = {"EnterpriseManagement"})
//    public void destroy() {
//        System.out.println("destroy driver");
//        driver.close();
//    }

    @Test(groups = {"BasicOperation.logIn"},dependsOnGroups = {"BasicOperationTest.init"})
    @Parameters({"account","password","loggedName"})
    public void logIn(String account,String password,String loggedName) {
        driver.get("https://192.168.0.100:3999/login");
        ((ChromeDriver) driver).findElementByXPath(UIPathBasicEnum.getPath("login_name")).sendKeys(account);
        ((ChromeDriver) driver).findElementByXPath(UIPathBasicEnum.getPath("login_pwd")).sendKeys(password);
        driver.findElement(By.xpath(UIPathBasicEnum.getPath("login_confirm"))).click();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        String text = driver.findElement(By.xpath(UIPathBasicEnum.getPath("logged_name"))).getText();
        Assert.assertEquals(text, loggedName);
//        driver.close();
    }




}