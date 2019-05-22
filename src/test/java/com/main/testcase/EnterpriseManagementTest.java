package com.main.testcase;

import com.main.data.ExcelData;
import com.main.driver.ChromeDriverSingleton;
import com.main.uienum.UIPathOriganizationEnum;
import jxl.read.biff.BiffException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

    @Test(groups = {"EnterpriseManagementTest.init"}, dependsOnGroups = {"BasicOperationTest.init"})
    public void initDriver() {
        System.out.println("get driver");
        driver = ChromeDriverSingleton.getInstance();
    }

    @Test(groups = {"EnterpriseManagement.AddEmployee"}, dependsOnGroups = {"BasicOperation.logIn", "EnterpriseManagementTest.init"}, dataProvider = "employee_data")
//    @Parameters({"employeeName", "employeeSex", "employeeEmail", "employeePassword", "employeePhone", "employeeBrief", "employeePosition"})
    public void testAddEmployee(HashMap<String, String> data) {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(UIPathOriganizationEnum.getPath("enterprise_organization"))));

        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_organization"))).click();
        driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_add_employee_button"))).click();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_add_employee_name"))).sendKeys(data.get("employeeName"));
        driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_add_employee_email"))).sendKeys(data.get("employeeEmail"));
        driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_add_employee_password"))).sendKeys(data.get("employeePassword"));
        driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_add_employee_phone"))).sendKeys(data.get("employeePhone"));
        driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_add_employee_brief"))).sendKeys(data.get("employeeBrief"));
        driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_add_employee_position"))).sendKeys(data.get("employeePosition"));
        if (data.get("employeeSex").equals("male")) {
            driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_add_employee_male_button"))).click();
        } else {
            driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_add_employee_female_button"))).click();
        }
        // submit
        driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_add_employee_submit_button"))).click();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //delete
        testDeleteEmployee(data);

    }

//    @Test(groups = "EnterpriseManagement.DeleteEmployee", dependsOnGroups = "EnterpriseManagement.AddEmployee", dataProvider = "employee")
    private void testDeleteEmployee(HashMap<String, String> data) {
        //delete
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
//        driver.navigate().refresh();
        System.out.println(driver.findElement(By.xpath(UIPathOriganizationEnum.getPath("enterprise_last_employee"))).getText());
        // get tbody
        WebElement tbody = driver.findElement(By.xpath("//*[@id=\"right-table\"]/div[2]/div/div[2]/table/tbody"));
        List<WebElement> lines = tbody.findElements(By.tagName("tr"));

        for (int i = 1; i <= lines.size(); i++) {
            if (driver.findElement(By.xpath("//*[@id=\"right-table\"]/div[2]/div/div[2]/table/tbody/tr["+i+"]/td[3]/div/span")).getText().equals(data.get("employeeName"))) {
                driver.findElement(By.xpath("//*[@id=\"right-table\"]/div[2]/div/div[2]/table/tbody/tr["+i+"]/td[1]/div/label/span/input")).click();
                //delete
                driver.findElement(By.xpath("//*[@id=\"right-table\"]/div[1]/div[1]/button[2]")).click();
                //confirm
                driver.findElement(By.xpath("/html/body/div[7]/div[2]/div/div/div[2]/div[2]/div[3]/button[2]")).click();
                System.out.println("deleted user");
            }

        }

    }


}
