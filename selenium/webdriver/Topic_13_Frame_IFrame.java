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
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_Frame_IFrame {
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
		action = new Actions(driver);
	}
//	@Test
	public void TC_01_Frame() throws InterruptedException {
		driver.get("https://kyna.vn/");
		List<WebElement> popup = driver.findElements(By.xpath("//div[@class='fancybox-skin']"));
		if (popup.size()==1) {
			driver.findElement(By.xpath("//a[@title='Close']")).click();		
		}
	
		driver.switchTo().frame(driver.findElement(By.cssSelector("div.fanpage iframe")));
		Assert.assertEquals(driver.findElement(By.cssSelector("div._1drq")).getText(), "168K lượt thích");
		driver.switchTo().defaultContent();
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//footer")));
		sleep(3);
		driver.switchTo().frame(driver.findElement(By.cssSelector("iframe#cs_chat_iframe")));
		driver.findElement(By.cssSelector("div.button_bar")).click();
		String name = "John Wick";
		String phone = "0987789789";
		String message = "Java Bootcamp";
		
		driver.findElement(By.cssSelector("input.input_name")).sendKeys(name);
		driver.findElement(By.cssSelector("input.input_phone")).sendKeys(phone);
		driver.findElement(By.cssSelector("select#serviceSelect")).click();
		driver.findElement(By.xpath("//select[@id='serviceSelect']/option[text()='TƯ VẤN TUYỂN SINH']")).click();
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//textarea[@name='message']")));
		driver.findElement(By.xpath("//textarea[@name='message']")).sendKeys(message);
		driver.findElement(By.cssSelector("input.submit")).click();
		sleep(2);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.scrollable_inner label.logged_in_name")).getText(), name);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.scrollable_inner label.logged_in_phone")).getText(), phone);
//		Assert.assertEquals(driver.findElement(By.cssSelector("textarea.input_textarea ")), message);
		driver.switchTo().defaultContent();
		WebElement searchBar = driver.findElement(By.xpath("//input[@id='live-search-bar']"));
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", searchBar);
		searchBar.sendKeys("Excel");
		searchBar.sendKeys(Keys.ENTER);
		List<WebElement> course = driver.findElements(By.xpath("//div[@class='content']/h4"));
		Assert.assertEquals(course.size(), 10);
		for(WebElement courseEach: course) {
			Assert.assertTrue(courseEach.getText().toLowerCase().contains("excel"));
			System.out.println(courseEach.getText());
		}
	}
		
	@Test
	public void TC_02_Frame_IFrame() {
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		driver.switchTo().frame("login_page");
		driver.findElement(By.cssSelector("input[name='fldLoginUserId']")).sendKeys("automationfc");
		driver.findElement(By.cssSelector("a[class*='login-btn']")).click();
	}

//	@Test
	public void TC_05_Random_Popup() {
		driver.get("");
	}
	
//	@Test
	public void TC_06_Random_Popup() {
		driver.get("");
	}
	
//	@Test
	public void TC_07_Random_Popup() {
		driver.get("");
	}
	
//	@Test
	public void TC_08_Random_Popup() {
		driver.get("");
	}
	
//	@Test
	public void TC_09_Random_Popup() {
		driver.get("");
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