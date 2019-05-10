package com.tzq.auto;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class WebTour {

	private ChromeDriver driver = null;

	public static void screenShot(String storagePlace, ChromeDriver driver) throws IOException {
		
	}


	@Parameters({ "NAME", "PWD", "LASTNAME", "CREADIT", "STORAGEURL", "FIRSTNAME","TITLE"  })
	@Test
	public void testServices(String NAME, String PWD, String LASTNAME, String CREADIT, String STORAGEURL,
			String FIRSTNAME,String TITLE) {
		Assert.assertEquals(driver.getTitle(), TITLE);
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		WebElement linkText = driver.findElementByLinkText("administration");
		String attribute = linkText.getAttribute("href");
		System.out.println("administration³¬Á´½Ó:" + attribute);
		doBookBehaviour(NAME,PWD,LASTNAME,CREADIT,STORAGEURL,FIRSTNAME);
		doBookBehaviour(NAME,PWD,LASTNAME,CREADIT,STORAGEURL,FIRSTNAME);
		doBookBehaviour(NAME,PWD,LASTNAME,CREADIT,STORAGEURL,FIRSTNAME);
	}
		
		private void doBookBehaviour(String NAME, String PWD, String LASTNAME, String CREADIT, String STORAGEURL,
				String FIRSTNAME) {

			//wait for signin page
			WebDriverWait waitSign = new WebDriverWait(driver, 5);
			waitSign.until(new ExpectedCondition<WebElement>() {
				public WebElement apply(WebDriver d) {
					d.switchTo().defaultContent();
					d.switchTo().frame("body");
					d.switchTo().frame("navbar");
					return d.findElement(By.name("username"));
				}
			});
			driver.switchTo().defaultContent();
			driver.switchTo().frame("body");
			driver.switchTo().frame("navbar");
			WebElement username = driver.findElementByName("username");
			WebElement pwd = driver.findElementByName("password");
			WebElement login = driver.findElementByName("login");
			username.sendKeys(NAME);
			pwd.sendKeys(PWD);
			login.submit();

			// jump to flights
			WebDriverWait waitFlights = new WebDriverWait(driver, 5);
			waitFlights.until(new ExpectedCondition<WebElement>() {
				public WebElement apply(WebDriver d) {
					d.switchTo().defaultContent();
					d.switchTo().frame("body");
					d.switchTo().frame("navbar");
					return d.findElement(By.tagName("a"));
				}
			});
			driver.switchTo().defaultContent();
			driver.switchTo().frame("body");
			driver.switchTo().frame("navbar");
			List<WebElement> aList = driver.findElementsByTagName("a");
			WebElement flightLink = aList.get(0);

			flightLink.click();

			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(new ExpectedCondition<WebElement>() {
				public WebElement apply(WebDriver d) {
					d.switchTo().defaultContent();
					d.switchTo().frame("body");
					d.switchTo().frame("info");
					return d.findElement(By.name("depart"));
				}
			});

			new Select(driver.findElement(By.name("depart"))).selectByIndex(3);
			;
			new Select(driver.findElement(By.name("arrive"))).selectByValue("Denver");

			driver.findElement(By.name("roundtrip")).click();

			WebElement returnDate = driver.findElement(By.name("returnDate"));
			returnDate.clear();
			returnDate.sendKeys("05/30/2018");

			driver.findElement(By.name("findFlights")).click();

		
			WebDriverWait waitForBookPage = new WebDriverWait(driver, 5);
			waitForBookPage.until(new ExpectedCondition<WebElement>() {
				public WebElement apply(WebDriver d) {
					d.switchTo().defaultContent();
					d.switchTo().frame("body");
					d.switchTo().frame("info");
					return d.findElement(By.name("reserveFlights"));
				}
			});
			driver.findElement(By.name("reserveFlights")).click();

			WebDriverWait waitForPayPage = new WebDriverWait(driver, 5);
			waitForPayPage.until(new ExpectedCondition<WebElement>() {
				public WebElement apply(WebDriver d) {
					d.switchTo().defaultContent();
					d.switchTo().frame("body");
					d.switchTo().frame("info");
					return d.findElement(By.name("buyFlights"));
				}
			});
			// fill into
			WebElement firstName = driver.findElementByName("firstName");
			firstName.clear();
			firstName.sendKeys(FIRSTNAME);
			WebElement lastName = driver.findElementByName("lastName");
			lastName.clear();
			lastName.sendKeys(LASTNAME);
			WebElement creditCard = driver.findElementByName("creditCard");
			creditCard.clear();
			creditCard.sendKeys(CREADIT);
			driver.findElement(By.name("buyFlights")).click();
			// waitForIvocePage
			WebDriverWait waitForIvocePage = new WebDriverWait(driver, 5);
			waitForIvocePage.until(new ExpectedCondition<WebElement>() {
				public WebElement apply(WebDriver d) {
					d.switchTo().defaultContent();
					d.switchTo().frame("body");
					d.switchTo().frame("info");
					return d.findElement(By.name("Book Another"));
				}
			});
			try {
				File screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(screenshotAs, new File(STORAGEURL));
			} catch (IOException e) {
				e.printStackTrace();
			}
			driver.switchTo().defaultContent();
			driver.switchTo().frame("body");
			driver.switchTo().frame("navbar");
			aList = driver.findElementsByTagName("a");
			aList.get(1).click();
			WebDriverWait waitForCancelPage = new WebDriverWait(driver, 5);
			waitForCancelPage.until(new ExpectedCondition<WebElement>() {
				public WebElement apply(WebDriver d) {
					d.switchTo().defaultContent();
					d.switchTo().frame("body");
					d.switchTo().frame("info");
					return d.findElement(By.name("removeAllFlights"));
				}
			});
		
		driver.findElement(By.name("removeAllFlights")).click();


		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("navbar");
		aList = driver.findElementsByTagName("a");
		aList.get(3).click();

	}

	@Parameters({ "URL", "PROPERTY_KEY", "PROPERTY_VALUE" })
	@BeforeMethod
	public void setUp(String URL, String PROPERTY_KEY, String PROPERTY_VALUE) {
		System.setProperty(PROPERTY_KEY, PROPERTY_VALUE);
		driver = new ChromeDriver();
		driver.get(URL);
	}


}
