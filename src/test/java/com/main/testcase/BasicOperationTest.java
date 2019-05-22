package com.main.testcase;

import com.main.data.ExcelData;
import com.main.uienum.UIPathBasicEnum;
import com.main.driver.ChromeDriverSingleton;
import jxl.read.biff.BiffException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.HashMap;
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

    @DataProvider(name = "login")
    public Object[][] getLoginData() {
        try {
            return new ExcelData().getExcelData("login", "test_data.xls");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return null;
    }
//    @Test(groups = {"BasicOperationTest.destroy"}, dependsOnGroups = {"EnterpriseManagement"})
//    public void destroy() {
//        System.out.println("destroy driver");
//        driver.close();
//    }

    @Test(groups = {"BasicOperation.logIn"},dependsOnGroups = {"BasicOperationTest.init"})
//    @Parameters({"account","password","loggedName"})
    public void logIn(HashMap<String,String> data) {
        driver.get("https://192.168.0.100:3999/login");
        ((ChromeDriver) driver).findElementByXPath(UIPathBasicEnum.getPath("login_name")).sendKeys(data.get("account"));
        ((ChromeDriver) driver).findElementByXPath(UIPathBasicEnum.getPath("login_pwd")).sendKeys(data.get("password"));
        driver.findElement(By.xpath(UIPathBasicEnum.getPath("login_confirm"))).click();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        String text = driver.findElement(By.xpath(UIPathBasicEnum.getPath("logged_name"))).getText();
        Assert.assertEquals(text, data.get("loggedName"));
//        driver.close();
    }




}