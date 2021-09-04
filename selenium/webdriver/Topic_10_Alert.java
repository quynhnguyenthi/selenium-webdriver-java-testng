package webdriver;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_10_Alert {
	WebDriver driver;
	Random rand;
	String projectPath = System.getProperty("user.dir");

	JavascriptExecutor jsExecutor;
	WebDriverWait explicitWait;
	Alert alert;
	String fullName, username, password, url;
	String chromeAuthAutoIT = projectPath + "\\autoIT\\authen_chrome.exe";
	String firefoxAuthAutoIT = projectPath + "\\autoIT\\authen_firefox.exe"; 

	@BeforeClass
	public void beforeClass() {
//		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
//		driver = new ChromeDriver();
		
		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		driver = new FirefoxDriver();
		
		explicitWait = new WebDriverWait(driver, 10);
		jsExecutor = (JavascriptExecutor) driver;

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		fullName = "batMan";
		username = "admin";
		password = "admin";

	}

//	@Test
	public void TC_01_Accept_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//button[normalize-space()='Click for JS Alert']")).click();

		alert = driver.switchTo().alert();
		System.out.println(alert.getText());
		Assert.assertEquals(alert.getText(), "I am a JS Alert");
		alert.accept();

		Assert.assertEquals(driver.findElement(By.xpath("//p[@id='result']")).getText(),
				"You clicked an alert successfully");

	}

//	@Test
	public void TC_01_Accept_Alert_Guru() {
		driver.get("https://demo.guru99.com/V4/index.php");
		driver.findElement(By.name("btnLogin")).click();

//		Wait for alert to show up
		alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		alert.accept();
	}

//	@Test
	public void TC_02_Confirm_Alert() throws InterruptedException {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		jsExecutor.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath("//button[normalize-space()='Click for JS Confirm']")));
		sleep(3);
		driver.findElement(By.xpath("//button[normalize-space()='Click for JS Confirm']")).click();

		alert = driver.switchTo().alert();
		System.out.println(alert.getText());
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");
		alert.accept();

		Assert.assertEquals(driver.findElement(By.xpath("//p[@id='result']")).getText(), "You clicked: Ok");

		//Cancel confirm alert
		driver.findElement(By.xpath("//button[normalize-space()='Click for JS Confirm']")).click();
		alert = driver.switchTo().alert();
		System.out.println(alert.getText());
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");
		alert.dismiss();

		Assert.assertEquals(driver.findElement(By.xpath("//p[@id='result']")).getText(), "You clicked: Cancel");
	}
	
//	@Test
	public void TC_03_Prompt_Alert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		jsExecutor.executeScript("arguments[0].scrollIntoView(true)", driver.findElement(By.xpath("//button[normalize-space()='Click for JS Confirm']")));
		driver.findElement(By.xpath("//button[normalize-space()='Click for JS Prompt']")).click();
		alert = driver.switchTo().alert();
		System.out.println(alert.getText());
		Assert.assertEquals(alert.getText(), "I am a JS prompt");
		alert.sendKeys(fullName);
		
		alert.accept();
		Assert.assertEquals(driver.findElement(By.xpath("//p[@id='result']")).getText(), "You entered: " + fullName);
		
//		Cancel prompt alert
		driver.findElement(By.xpath("//button[normalize-space()='Click for JS Prompt']")).click();
		alert = driver.switchTo().alert();
		alert.dismiss();
		Assert.assertEquals(driver.findElement(By.xpath("//p[@id='result']")).getText(), "You entered: null");
	}
	
//	@Test
	public void TC_04_Authentication_Alert() {
		url = "http://the-internet.herokuapp.com/basic_auth";
		String[] list = url.split("//");
		System.out.println(list[0]);
		System.out.println(list[1]);
		url = list[0] + "//" + username + ":" + password + "@" + list[1];
		driver.get(url);
		
		Assert.assertEquals(driver.findElement(By.xpath("//p")).getText(), 
				"Congratulations! You must have the proper credentials.");
		System.out.println(driver.findElement(By.xpath("//p")).getText());
	}
	
	@Test
	public void TC_05_Authentication_Alert_AutoIT() throws InterruptedException, IOException {
//		Execute script before opening app
		Runtime.getRuntime().exec(new String[] {firefoxAuthAutoIT, username, password});
		
		driver.get("https://the-internet.herokuapp.com/basic_auth");
		sleep(20);
		By successfulAuth = By.xpath("//p[contains(text(),'Congratulations! You must have the proper credentials.')]");
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(successfulAuth));
		Assert.assertEquals(driver.findElement(successfulAuth).getText(), 
				"Congratulations! You must have the proper credentials.");
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

	public void clickByJS(By by) {
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(by));
	}

	public String generateEmail() {
		rand = new Random();
		return rand.nextInt(9999) + "@mail.vn";
	}

	public void sleep(int value) throws InterruptedException {
		Thread.sleep(value * 1000);
	}

	public void clickRadioButton(By by) {
		if (!driver.findElement(by).isSelected()) {
			driver.findElement(by).click();
			System.out.println("Radio is now clicked");
		} else
			System.out.println("Radio is already clicked");
	}

	public void clickAcceptCookies() {
		List<WebElement> acceptCookiesButton = driver
				.findElements(By.cssSelector("button#onetrust-accept-btn-handler"));
		System.out.println(acceptCookiesButton.size());
		int sizeAcceptCookies = acceptCookiesButton.size();
		if (sizeAcceptCookies == 1) {
			if (driver.findElement(By.cssSelector("button#onetrust-accept-btn-handler")).isDisplayed()) {
				driver.findElement(By.cssSelector("button#onetrust-accept-btn-handler")).click();
			}
		}
	}
}