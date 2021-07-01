package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_03_Selenium_Locator {
	// Khai báo 1 biến đại diện cho Selenium Webdriver
	WebDriver driver;
	// Vị trí Chromedriver
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		// System.setProperty("webdriver.gecko.driver",
		// ".\\browserDrivers\\geckodriver.exe");
		// driver = new FirefoxDriver();

		// Trình duyệt chạy - Chrome
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		// Mở trình duyệt chạy lên
		driver = new ChromeDriver();

		// Set timeout để tìm element
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		// Mở application lên (AUT/SUT)
		driver.get("http://live.demoguru99.com/index.php/customer/account/login/");
	}

	public void TC_01_FindElement() {
		// Single element: Element
		WebElement loginButton = driver.findElement(By.className(""));
		loginButton.click();

		// Multiple elements: List<WebElement>
		List<WebElement> buttons = driver.findElements(By.className(""));
		buttons.get(0).click();
	}

	@Test
	public void TC_02_ID() {
		// click button Login without any previous input
		driver.findElement(By.id("send2")).click();

		// verify error message xuat hien
		Assert.assertTrue(driver.findElement(By.id("advice-required-entry-email")).isDisplayed());
	}

	@Test
	public void TC_03_Class() {
		driver.navigate().refresh();
		driver.findElement(By.className("validate-email")).sendKeys("dam@gmail.com");;

	}

	@Test
	public void TC_04_Name() {
		driver.navigate().refresh();
		driver.findElement(By.name("send")).click();
		
		Assert.assertTrue(driver.findElement(By.id("advice-required-entry-email")).isDisplayed());

	}

	@Test
	public void TC_05_TagName() {
		//lay het tat ca duong link tai man hinh nay va get text
		driver.navigate().refresh();
		List<WebElement> loginPageLinks = driver.findElements(By.tagName("a"));
		
		for (WebElement webElement : loginPageLinks) {
			System.out.println(webElement.getText());
		}
	
	}

	@Test
	public void TC_06_LinkText() {
		driver.navigate().refresh();
		driver.findElement(By.linkText("Forgot Your Password?")).click();
		Assert.assertTrue(driver.findElement(By.id("email_address")).isDisplayed());

	}

	@Test
	public void TC_07_PartialLinkText() {
		
		driver.findElement(By.partialLinkText("Back to")).click();
		Assert.assertTrue(driver.findElement(By.id("email")).isDisplayed());
		
	}

	@Test
	public void TC_08_Css() {
		driver.navigate().refresh();
		driver.findElement(By.cssSelector("#email")).sendKeys("dam@gmail.com");
		driver.findElement(By.cssSelector("input[id='pass']")).sendKeys("12456789");

	}

	@Test
	public void TC_09_Xpath() {
		driver.navigate().refresh();
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("dam@gmail.com");
		driver.findElement(By.xpath("//input[@name='login[password]']")).sendKeys("1245678");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
