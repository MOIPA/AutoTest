package com.main.testcase;

import com.main.data.ExcelData;
import com.main.driver.ChromeDriverSingleton;
import com.main.uienum.UIPathOriganizationEnum;
import jxl.read.biff.BiffException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Test()
public class EnterpriseManagementTest {

    private WebDriver driver;
    private BasicOperationTest basicOperationTest;

    @DataProvider(name = "employee_data")
    public Object[][] getEmployeeData() {
        try {
            return new ExcelData().getExcelData("employee", "test_data.xls");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test(groups = {"EnterpriseManagementTest.init"},dependsOnGroups = {"BasicOperationTest.init"})
    public void initDriver() {
        System.out.println("get driver");
        driver = ChromeDriverSingleton.getInstance();
    }

    @Test(groups = {"EnterpriseManagement.AddEmployee"}, dependsOnGroups = {"BasicOperation.logIn", "EnterpriseManagementTest.init"},dataProvider = "employee_data")
//    @Parameters({"employeeName", "employeeSex", "employeeEmail", "employeePassword", "employeePhone", "employeeBrief", "employeePosition"})
    public void testAddEmployee(String employeeName, String employeeSex, String employeeEmail, String employeePassword, String employeePhone, String employeeBrief, String employeePosition) {
        driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_organization"))).click();
        driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_add_employee_button"))).click();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_add_employee_name"))).sendKeys(employeeName);
        driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_add_employee_email"))).sendKeys(employeeEmail);
        driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_add_employee_password"))).sendKeys(employeePassword);
        driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_add_employee_phone"))).sendKeys(employeePhone);
        driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_add_employee_brief"))).sendKeys(employeeBrief);
        driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_add_employee_position"))).sendKeys(employeePosition);
        if (employeeSex.equals("male")) {
            driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_add_employee_male_button"))).click();
        } else {
            driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_add_employee_female_button"))).click();
        }
        // submit
        driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_add_employee_submit_button"))).click();


    }

    @Test(groups = "EnterpriseManagement.DeleteEmployee", dependsOnGroups = "EnterpriseManagement.AddEmployee")
    @Parameters("employeeName")
    public void testDeleteEmployee (String employeeName){
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
