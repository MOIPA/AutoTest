package com.main.testcase.basic;

import com.main.data.ExcelData;
import com.main.uienum.LoginUiPathEnum;
import com.main.driver.ChromeDriverSingleton;
import jxl.read.biff.BiffException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

@Test()
public class Login {

    private WebDriver driver;


    @Test(groups = {"Login.init"})
//    @BeforeMethod
    public void init() {
        System.out.println("init driver");
        driver = ChromeDriverSingleton.getInstance();
    }

    @Test(groups = {"Login.tearDown"})
    public void tearDown() {
        driver.quit();
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        try {
            return new ExcelData().getExcelData("Login", "test_data.xls");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * excel 表中第一行必须存放可以登陆的数据，否则后面依赖的测试无法执行前置登陆
     * @param data
     * @throws Exception
     */
    @Test(groups = {"Login.logIn"}, dataProvider = "loginData")
//    @Parameters({"account","password","loggedName"})
    public void login(HashMap<String, String> data) throws Exception {
        WebElement until;
        driver.navigate().to(data.get("url"));
        ((ChromeDriver) driver).findElementByXPath(LoginUiPathEnum.getPath("login_name")).sendKeys(data.get("account"));
        ((ChromeDriver) driver).findElementByXPath(LoginUiPathEnum.getPath("login_pwd")).sendKeys(data.get("password"));
        driver.findElement(By.xpath(LoginUiPathEnum.getPath("login_confirm"))).click();
        // get warning
        until = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.className("ivu-message-notice")));
        String message = until.getText();
//        System.out.println("the message:"+message.equals("登录成功"));
        if (message.equals("登录成功")) {
            until = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(data.get("check_point"))));
            String text = until.getText();
            ExcelData.AssertEqual(text, data.get("loggedName"), data.get("ID"));
        } else {
            ExcelData.AssertEqual(message, "账号认证失败", data.get("ID"));
        }
    }


}