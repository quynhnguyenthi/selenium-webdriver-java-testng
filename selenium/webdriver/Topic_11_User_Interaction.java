package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_User_Interaction {
	WebDriver driver;
	Random rand;
	String projectPath = System.getProperty("user.dir");

	JavascriptExecutor jsExecutor;
	WebDriverWait explicitWait;
	Alert alert;
	String fullName, username, password, url;
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
		fullName = "batMan";
		username = "admin";
		password = "admin";
		action = new Actions(driver);
	}

//	@Test
	public void TC_01_Hover() throws InterruptedException {
		driver.get("https://automationfc.github.io/jquery-tooltip/");
		WebElement field = driver.findElement(By.xpath("//input[@id='age']"));
		action.moveToElement(field).perform();
		Assert.assertEquals(driver.findElement(By.className("ui-tooltip-content")).getText(),
				"We ask for your age only for statistical purposes.");
		sleep(10);
	}

//	@Test
	public void TC_02_Hover() throws InterruptedException {
		driver.get("http://www.myntra.com/");
		sleep(3);
		WebElement kid = driver.findElement(By.xpath("//a[@class='desktop-main'][normalize-space()='Kids']"));
		action.moveToElement(kid).perform();
		WebElement subWatches = driver.findElement(By.xpath("//a[@class='desktop-categoryLink' and text()='Watches']"));
		subWatches.click();
		sleep(3);
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='breadcrumbs-crumb' and text()='Kids Watches']")).isDisplayed());
		Assert.assertEquals(driver.getCurrentUrl(), "https://www.myntra.com/kids-watches");
	}
	
//	@Test
	public void TC_03_Hover() throws InterruptedException {
		driver.get("https://www.fahasa.com/");
		sleep(3);
		WebElement menu = driver.findElement(By.xpath("//div[@class=\"col-md-3 hidden-max-width-992\"]//span[@class='menu-title' and text()='Sách Trong Nước']"));
		action.moveToElement(menu).perform();
		WebElement subMenu = driver.findElement(By.xpath("//div[contains(@class,'col-md-3 hidden-max-width-992')]//a[contains(text(),'Nhân Vật - Bài Học Kinh Doanh')]"));
		Assert.assertTrue(subMenu.isDisplayed());
		
	}

	@Test
	public void TC_04_Click_Hold() {
		
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