package com.tr.testng;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class MySelenium {
	
	public static String URL = "http://127.0.0.1:1080/WebTours/";
	public static String TITLE = "Web Tours";
	public static String NAME = "tr";
	public static String PWD = "123123";
	public static String PROPERTY_KEY = "webdriver.firefox.marionette";
	public static String PROPERTY_VALUE = "f:\\geckodriver.exe";
	public static String FIRSTNAME = "tang";
	public static String LASTNAME = "rui";
	public static String CREADIT = "2015020800117";
	public static String STORAGEURL = "F:/invoice.jpg";
	

	public static void main(String[] args) {
		// init properties and retur driver
		FirefoxDriver driver = init(URL);

		Assert.assertEquals(driver.getTitle(), TITLE);

		driver.switchTo().frame("body");
		driver.switchTo().frame("info");
		WebElement linkText = driver.findElementByLinkText("administration");
		String attribute = linkText.getAttribute("href");
		System.out.println("administration超链接:" + attribute);

		// sign in
		signIn(NAME, PWD, driver);
		// get cookies and print out
		getAllCookies(driver);
		// order flights
		try {
			orderFlights(driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// driver.quit();
		// driver.close();
	}

	/**
	 * init Driver
	 * 
	 * @param url
	 *            web url
	 * @return firefox driver
	 */
	public static FirefoxDriver init(String url) {
		System.setProperty(PROPERTY_KEY, PROPERTY_VALUE);
		FirefoxDriver driver = new FirefoxDriver();
		driver.get(url);
		return driver;
	}

	public static void signIn(String name, String password, FirefoxDriver driver) {
		// driver.navigate().to("http://www.jlxy.edu.cn");// jump
		// saveButton.click(); //click button
		// element.sendKeys(“test”); //input text

		// driver.switchTo().frame("body");
		driver.switchTo().parentFrame();
		driver.switchTo().frame("navbar");
		WebElement username = driver.findElementByName("username");
		WebElement pwd = driver.findElementByName("password");
		WebElement login = driver.findElementByName("login");
		username.sendKeys(name);
		pwd.sendKeys(password);
		login.submit();
	}

	public static WebElement getCell(WebElement Row, int cell) {
		List<WebElement> cells;
		WebElement target = null;
		if (Row.findElements(By.tagName("th")).size() > 0) {
			cells = Row.findElements(By.tagName("th"));
			target = cells.get(cell);
		}
		if (Row.findElements(By.tagName("td")).size() > 0) {
			cells = Row.findElements(By.tagName("td"));
			target = cells.get(cell);
		}
		return target;
	}

	public static String getCellText(By by, String tableCellAddress, FirefoxDriver driver) {
		WebElement table = driver.findElement(by);
		int index = tableCellAddress.trim().indexOf('.');
		int row = Integer.parseInt(tableCellAddress.substring(0, index));
		int cell = Integer.parseInt(tableCellAddress.substring(index + 1));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		WebElement theRow = rows.get(row);

		return getCell(theRow, cell).getText();

	}

	public static void getAllCookies(FirefoxDriver driver) {
		/**
		 * need to be executed after signIn()
		 */
		// 得到当前页面下所有的cookies，并且输出它们的所在域、name、value、有效日期和路径

		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver d) {
				d.switchTo().defaultContent();
				return d.findElement(By.name("body"));
			}
		});

		Options manage = driver.manage();
		Set<Cookie> cookies = manage.getCookies();

		System.out.println(String.format("Domain -> name -> value -> expiry -> path"));
		for (Cookie c : cookies)
			System.out.println(String.format("%s -> %s -> %s -> %s -> %s", c.getDomain(), c.getName(), c.getValue(),
					c.getExpiry(), c.getPath()));
	}

	public static void screenShot(String storagePlace, FirefoxDriver driver) throws IOException {
		File screenshotAs = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotAs, new File(storagePlace));
	}

	public static void orderFlights(FirefoxDriver driver) throws IOException {
		// jump to flights
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("navbar");
		List<WebElement> aList = driver.findElementsByTagName("a");
		WebElement flightLink = aList.get(0);
		// for(WebElement item :aList)
		// System.out.println(item.getAttribute("href"));
		// System.out.println(flightLink.getAttribute("href"));
		// jump
		flightLink.click();
		// wait
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
		new Select(driver.findElement(By.name("arrive"))).selectByValue("Denver");
		// click roundtrip single checkbox
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
		screenShot(STORAGEURL, driver);
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

		driver.findElement(By.name("removeAllFlights")).click();

		// sign off
		driver.switchTo().defaultContent();
		driver.switchTo().frame("body");
		driver.switchTo().frame("navbar");
		aList = driver.findElementsByTagName("a");
		aList.get(3).click();
	}

}
