package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_12_PopUp {
	WebDriver driver;
	Random rand;
	String projectPath = System.getProperty("user.dir");

	JavascriptExecutor jsExecutor;
	WebDriverWait explicitWait;
	Alert alert;
	String chromeAuthAutoIT = projectPath + "\\autoIT\\authen_chrome.exe";
	String firefoxAuthAutoIT = projectPath + "\\autoIT\\authen_firefox.exe";
	Actions action;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
//		System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
//		driver = new FirefoxDriver();
		explicitWait = new WebDriverWait(driver, 10);
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		action = new Actions(driver);
	}
//	@Test
	public void TC_01_Fixed_PopUp() throws InterruptedException {
		driver.get("https://ngoaingu24h.vn/");
		driver.findElement(By.xpath("//button[@class='login_ icon-before']")).click();
		sleep(3);
		driver.findElement(By.xpath("//input[@id='account-input']")).sendKeys("auto");
		driver.findElement(By.xpath("//input[@id='password-input']")).sendKeys("123456");
		driver.findElement(By.cssSelector("button[class*='btn-login-v1']")).click();
		driver.findElement(By.xpath("//div[text()='Tài khoản không tồn tại!']")).isDisplayed();
		Assert.assertTrue(driver.findElement(By.cssSelector("div#modal-login-v1 div.modal-header")).isDisplayed());
		driver.findElement(By.xpath("//div[@id='modal-login-v1']//button[@class='close']")).click();
//		Assert.assertFalse(driver.findElement(By.cssSelector("div#modal-login-v1>div")).isDisplayed());
	}

//	@Test
	public void TC_02_Random_Popup() throws InterruptedException {
		driver.get("https://blog.testproject.io/");
		WebElement popUp = driver.findElement(By.xpath("//div[@class='mailch-wrap']"));

		if (popUp.isDisplayed()) {
			driver.findElement(By.xpath("//div[@id='close-mailch']//*[name()='svg']")).click();
			System.out.println("Popup is displayed");
			sleep(2);
		} else {
			System.out.println("Popup is not displayed");
		}

		driver.findElement(By.xpath("//section[@id='search-2']//input[@class='search-field']")).sendKeys("Selenium");
		driver.findElement(By.xpath("//section[@id='search-2']//input[@class='search-field']")).sendKeys(Keys.ENTER);
//		By search = By.xpath("//section[@id='search-2']//span[@class='glass']");
//
//		clickByJS(search);
		driver.findElement(By.xpath("//h2[@class='page-title']/span[contains(text(), 'Selenium')]")).isDisplayed();
		List<WebElement> article = driver.findElements(By.xpath("//h3[@class='post-title']/a"));
		for (WebElement eachArticle: article) {
			Assert.assertTrue(eachArticle.getText().contains("Selenium"));
		}
	}

	@Test
	public void TC_03_Random_Popup_Not_In_DOM() {
		driver.get("https://shopee.vn/");
		
		List<WebElement> popUp = driver.findElements(By.xpath("//div[@class='shopee-popup__container']")); 
		if(popUp.size()==1) {
			System.out.println("Popup is displayed");			
			driver.findElement(By.xpath("//div[@class='shopee-popup__close-btn']")).click();
		} else {
			System.out.println("Popup is not displayed");
		}
		driver.findElement(By.cssSelector("input[class*='shopee-searchbar']")).sendKeys("iPhone XS Mars");
		driver.findElement(By.cssSelector("input[class*='shopee-searchbar']")).sendKeys(Keys.ENTER);
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