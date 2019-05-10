package com.tr.testng;

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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class WebTour {

	private FirefoxDriver driver = null;

	public static void screenShot(String storagePlace, FirefoxDriver driver) throws IOException {
		File screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotAs, new File(storagePlace));
	}

	@Parameters({ "NAME", "PWD", "LASTNAME", "CREADIT", "STORAGEURL", "FIRSTNAME", "TITLE" })
	@Test
	public void testServices(String NAME, String PWD, String LASTNAME, String CREADIT, String STORAGEURL,
			String FIRSTNAME, String TITLE) throws InterruptedException {
		Assert.assertEquals(driver.getTitle(), TITLE);
		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		WebElement linkText = driver.findElementByLinkText("administration");
		String attribute = linkText.getAttribute("href");
		System.out.println("administration������:" + attribute);
		doLogin(NAME, PWD);
		doBookBehaviour(LASTNAME, CREADIT, STORAGEURL, FIRSTNAME,"Aisle");
		doBookBehaviour(LASTNAME, CREADIT, STORAGEURL, FIRSTNAME,"Window");
		doBookBehaviour(LASTNAME, CREADIT, STORAGEURL, FIRSTNAME,"None");
		doCancel();
		doSignOff();
	}

	private void doCancel() {
		// waitForCancelPage
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
	}

	private void doLogin(String NAME, String PWD) {
		// wait for signin page
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
	}

	private void doSignOff() {
		List<WebElement> aList = driver.findElementsByTagName("a");
		// sign off
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("navbar");
		aList = driver.findElementsByTagName("a");
		aList.get(3).click();
	}

	private void doBookBehaviour(String LASTNAME, String CREADIT, String STORAGEURL, String FIRSTNAME,String SEATPREF) throws InterruptedException {

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

		// for(WebElement item :aList)
		// System.out.println(item.getAttribute("href"));
		// System.out.println(flightLink.getAttribute("href"));
		// jump
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
		// set value of leaving and arrival city select item
		new Select(driver.findElement(By.name("depart"))).selectByIndex(3);
		;
		// click roundtrip single checkbox
		new Select(driver.findElement(By.name("arrive"))).selectByValue("Denver");

		//click seatpref 
		List<WebElement> seatPrefRadio = driver.findElements(By.name("seatPref"));
		if(SEATPREF.equals("Aisle")) {
			seatPrefRadio.get(0).click();
		}else if(SEATPREF.equals("Window")) {
			seatPrefRadio.get(1).click();
		}else if(SEATPREF.equals("None")) {
			seatPrefRadio.get(2).click();
		}
//		Thread.sleep(10000);
		driver.findElement(By.name("roundtrip")).click();
		// set return date
		WebElement returnDate = driver.findElement(By.name("returnDate"));
		returnDate.clear();
		returnDate.sendKeys("05/30/2018");
		// continue search flight
		driver.findElement(By.name("findFlights")).click();

		// waitForBookPage
		WebDriverWait waitForBookPage = new WebDriverWait(driver, 5);
		waitForBookPage.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver d) {
				d.switchTo().defaultContent();
				d.switchTo().frame("body");
				d.switchTo().frame("info");
				return d.findElement(By.name("reserveFlights"));
			}
		});
		// continue confirm flight
		driver.findElement(By.name("reserveFlights")).click();
		// waitForPayPage
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
			screenShot(STORAGEURL, this.driver);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// cancel booked
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("navbar");
		aList = driver.findElementsByTagName("a");
		aList.get(1).click();
		// waitForCancelPage
		WebDriverWait waitForCancelPage = new WebDriverWait(driver, 5);
		waitForCancelPage.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver d) {
				d.switchTo().defaultContent();
				d.switchTo().frame("body");
				d.switchTo().frame("info");
				return d.findElement(By.name("removeAllFlights"));
			}
		});
		// get table table has a lot of tables, hard to get the checkbox i need
		// ,giveitup
		// just cancel all order

		// List<WebElement> tables = driver.findElementsByTagName("table");
		// List<WebElement> rows = table.findElements(By.tagName("tr"));
		//// assertEquals(5,rows.size());
		// for(WebElement row:rows){
		// List<WebElement> cols= row.findElements(By.tagName("td"));
		// for(WebElement col:cols){
		// System.out.println(col.getText()+"\t");
		// }
		// System.out.println("");
		// }
		// for(int i=0;i<tables.size();i++){
		// WebElement table = tables.get(i+1);
		// }
		// String title = getCellText(By.tagName("table"), "1.1", driver);
		// System.out.println(title);

	}

	@Parameters({ "URL", "PROPERTY_KEY", "PROPERTY_VALUE" })
	@BeforeMethod
	public void setUp(String URL, String PROPERTY_KEY, String PROPERTY_VALUE) {
		System.setProperty(PROPERTY_KEY, PROPERTY_VALUE);
		driver = new FirefoxDriver();
		driver.get(URL);
	}

	@BeforeClass
	public void beforeClass() {
		System.out.println("hello");
	}

	@BeforeTest
	public void beforeTest() {
	}

}
