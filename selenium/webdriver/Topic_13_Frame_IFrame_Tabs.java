package webdriver;

import java.util.List;
import java.util.Random;
import java.util.Set;
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

public class Topic_13_Frame_IFrame_Tabs {
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
		
//	@Test
	public void TC_02_Frame_IFrame() {
		driver.get("https://netbanking.hdfcbank.com/netbanking/");
		driver.switchTo().frame("login_page");
		driver.findElement(By.cssSelector("input[name='fldLoginUserId']")).sendKeys("automationfc");
		driver.findElement(By.cssSelector("a[class*='login-btn']")).click();
		Assert.assertTrue(driver.findElement(By.id("fldPasswordDispId")).isDisplayed());
		driver.findElement(By.xpath("//div[@class='footer-link-lrg']//a[text()='Terms and Conditions']"));
	}
	
//	@Test
	public void TC_03_Windows_Tabs_ID() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
		String parentWindow = driver.getWindowHandle();
		System.out.println(driver.getTitle());
		switchToWindowByID(parentWindow);
		Assert.assertTrue(driver.getTitle().toLowerCase().contains("google"));
		
		driver.close();
		driver.switchTo().window(parentWindow);
		driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
		switchToWindowByID(parentWindow);
		Assert.assertTrue(driver.getTitle().toLowerCase().contains("facebook"));
		
		driver.close();
		driver.switchTo().window(parentWindow);
		driver.findElement(By.xpath("//a[text()='TIKI']")).click();
		switchToWindowByID(parentWindow);
		Assert.assertTrue(driver.getTitle().toLowerCase().contains("tiki"));
	}
	
//	@Test
	public void TC_04_Windows_Tabs_Titles() throws InterruptedException {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		clickLinkByText("GOOGLE");
		clickLinkByText("FACEBOOK");
		clickLinkByText("TIKI");
		clickLinkByText("LAZADA");
		switchToTabByTitle("tiki");
		driver.findElement(By.cssSelector("input[class*='FormSearch']")).sendKeys("sach" + Keys.ENTER);
		driver.findElement(By.xpath("//a[span='sach']")).isDisplayed();
		
		switchToTabByTitle("facebook");
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("automationfc@gmail.com");
		
		switchToTabByTitle("lazada");
		driver.findElement(By.xpath("//div[text()='Giảm Giá']")).click();
		driver.findElement(By.xpath("//span[text()='LAZADA VOUCHER']")).isDisplayed();
		
		closeExceptTitle("selenium");
	}
	
//	@Test
	public void TC_05_Windows_Tab() throws InterruptedException {
		driver.get("https://kyna.vn/");
		sleep(3);
		List<WebElement> popUp = driver.findElements(By.xpath("//a[@Title='Close']"));
		if (popUp.size()==1) {
			driver.findElement(By.xpath("//a[@Title='Close']")).click();	
		}
		
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//footer")));
		sleep(2);
		driver.findElement(By.cssSelector("ul[class='bottom'] + div a[href*='facebook']")).click();
		driver.findElement(By.cssSelector("ul[class='bottom'] + div a[href*='youtube']")).click();
		System.out.println(driver.getTitle());
		sleep(1);
		
		switchToTabByTitle("facebook");
		sleep(1);
		System.out.println(driver.getTitle());
		
		switchToTabByTitle("youtube");
		sleep(1);
		System.out.println(driver.getTitle());
		closeExceptTitle("online");
		switchToTabByTitle("online");
		System.out.println(driver.getTitle());
		sleep(5);
		
	}
	
	@Test
	public void TC_06_Windows() throws InterruptedException {
		driver.get("http://live.demoguru99.com/index.php/");
		driver.findElement(By.cssSelector("div[id*='header-nav'] li[class*='first']")).click();
		driver.findElement(By.xpath(
				"//a[text()='Samsung Galaxy']//parent::h2[@class='product-name']//following-sibling::div[@class='actions']//a[@class='link-compare']"))
				.click();
		driver.findElement(By.xpath(
				"//a[text()='Sony Xperia']//parent::h2[@class='product-name']//following-sibling::div[@class='actions']//a[@class='link-compare']"))
				.click();
		sleep(1);
		driver.findElement(By.xpath("//button[@title='Compare']")).click();
		switchToTabByTitle("comparison");
		Assert.assertTrue(driver.getTitle().equals("Products Comparison List - Magento Commerce"));
		driver.close();
		switchToTabByTitle("Mobile");
		driver.findElement(By.xpath("//a[text()='Clear All']")).click();
		alert = driver.switchTo().alert();
		alert.accept();
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='success-msg']//span[text()='The comparison list was cleared.']")).isDisplayed());
	
	}
	@AfterTest
	public void afterTest() {
		driver.quit();
	}
	
	public void closeExceptTitle (String titleTarget) {
		Set<String> allWindows = driver.getWindowHandles();
		for(String window : allWindows) {
			driver.switchTo().window(window);
			if (!driver.getTitle().toLowerCase().contains(titleTarget)) {
				driver.close();
			}			
		}
	}
	
	public void switchToTabByTitle(String titleTarget) {
		Set<String> allWindows = driver.getWindowHandles();
		for(String window : allWindows) {
			driver.switchTo().window(window);
			if (driver.getTitle().toLowerCase().contains(titleTarget)) {
				break;
			}			
		}
	}

	public void clickLinkByText(String text) {
		driver.findElement(By.xpath("//a[text()='" + text + "']")).click();
	}
	public void switchToWindowByID (String parentID) {
		Set<String> allWindows = driver.getWindowHandles();
		for(String window : allWindows) {
			if (!window.equals(parentID)) {
				driver.switchTo().window(window);
				System.out.println(driver.getTitle());
			}
		}
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
}