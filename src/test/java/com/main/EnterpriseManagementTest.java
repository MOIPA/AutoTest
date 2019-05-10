package com.main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

//@Test()
public class EnterpriseManagementTest {

    private WebDriver driver;
    private BasicOperationTest basicOperationTest;

    @Test(groups = {"EnterpriseManagementTest.init"},dependsOnGroups = {"BasicOperationTest.init"})
    public void initDriver() {
        System.out.println("get driver");
        driver = ChromeDriverSingleton.getInstance();
    }

    @Test(groups = {"EnterpriseManagement"}, dependsOnGroups = {"BasicOperation.logIn", "EnterpriseManagementTest.init"})
    @Parameters({"employeeName", "employeeSex", "employeeEmail", "employeePassword", "employeePhone", "employeeBrief", "employeePosition"})
    public void testAddEmployee(String employeeName, String employeeSex, String employeeEmail, String employeePassword, String employeePhone, String employeeBrief, String employeePosition) {
        driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]/div[1]/div/div[1]/div[1]/div/div[2]/div[3]")).click();
        WebElement addEmployee = driver.findElement(By.xpath("//*[@id=\"right-table\"]/div[1]/div[1]/button[1]/span"));
        addEmployee.click();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        driver.findElement(By.xpath("/html/body/div[13]/div[2]/div/div/div[2]/div/form/div[1]/div/div[1]/input")).sendKeys(employeeName);
        driver.findElement(By.xpath("/html/body/div[13]/div[2]/div/div/div[2]/div/form/div[3]/div/div[1]/input")).sendKeys(employeeEmail);
        driver.findElement(By.xpath("/html/body/div[13]/div[2]/div/div/div[2]/div/form/div[4]/div/div[1]/input")).sendKeys(employeePassword);
        driver.findElement(By.xpath("/html/body/div[13]/div[2]/div/div/div[2]/div/form/div[5]/div/div[1]/input")).sendKeys(employeePhone);
        driver.findElement(By.xpath("/html/body/div[13]/div[2]/div/div/div[2]/div/form/div[6]/div/div[1]/textarea")).sendKeys(employeeBrief);
        driver.findElement(By.xpath("/html/body/div[13]/div[2]/div/div/div[2]/div/form/div[7]/div/div/input")).sendKeys(employeePosition);
        if (employeeSex.equals("male")) {
            driver.findElement(By.xpath("/html/body/div[13]/div[2]/div/div/div[2]/div/form/div[2]/div/div/label[1]/span/input")).click();
        } else {
            driver.findElement(By.xpath("/html/body/div[13]/div[2]/div/div/div[2]/div/form/div[2]/div/div/label[2]/span/input")).click();
        }
        // submit
        driver.findElement(By.xpath("/html/body/div[13]/div[2]/div/div/div[3]/div/button[2]")).click();
        //delete
        driver.navigate().refresh();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        System.out.println(driver.findElement(By.xpath("//*[@id=\"right-table\"]/div[2]/div/div[2]/table/tbody/tr[last()]/td[3]/div/span")).getText());
        if (driver.findElement(By.xpath("//*[@id=\"right-table\"]/div[2]/div/div[2]/table/tbody/tr[last()]/td[3]/div/span")).getText().equals(employeeName)) {
            driver.findElement(By.xpath("//*[@id=\"right-table\"]/div[2]/div/div[2]/table/tbody/tr[last()]/td[1]/div/label/span/input")).click();
            driver.findElement(By.xpath("//*[@id=\"right-table\"]/div[1]/div[1]/button[2]")).click();
            driver.findElement(By.xpath("/html/body/div[7]/div[2]/div/div/div[2]/div[2]/div[3]/button[2]")).click();

        }

    }



}
