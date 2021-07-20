package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_03_Exercise_To_TC04 {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.get("http://live.demoguru99.com/");
	}

	@Test
	public void TC_01_Login_With_Empty_Email_Pass() {
		// click My account
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

		// click Login button
		driver.findElement(By.xpath("//button[@title='Login']")).click();

		// Verify 2 error messages
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='advice-required-entry-email']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='advice-required-entry-pass']")).isDisplayed());

	}

	@Test
	public void TC_02_Login_Invalid_Email() {
		driver.navigate().refresh();		
		// click My account
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

		//invalid email
		driver.findElement(By.xpath("//input[@name='login[username]']")).sendKeys("1232143@2343.23432");
		
		//valid password
		driver.findElement(By.xpath("//input[@name='login[password]']")).sendKeys("123456");
		// click Login button
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		
		//verify error message for email
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='advice-validate-email-email']")).getText(), "Please enter a valid email address. For example johndoe@domain.com.");
	}

	@Test
	public void TC_03_Login_Pass_Less_Than_06characters() {
		driver.navigate().refresh();		
		// click My account
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

		//valid email
		driver.findElement(By.xpath("//input[@name='login[username]']")).sendKeys("automation@gmail.com");
		
		//invalid password
		driver.findElement(By.xpath("//input[@name='login[password]']")).sendKeys("123");
		// click Login button
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		
		//verify error message for invalid password
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='advice-validate-password-pass']")).getText(), "Please enter 6 or more characters without leading or trailing spaces.");			
	}

	@Test
	public void TC_04_Incorrect_Email_Password() {
		driver.navigate().refresh();		
		// click My account
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

		//incorrect email
		driver.findElement(By.xpath("//input[@name='login[username]']")).sendKeys("automation@gmail.com");
		
		//incorrect password
		driver.findElement(By.xpath("//input[@name='login[password]']")).sendKeys("123123123");
		// click Login button
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		
		//verify error message about wrong info
		Assert.assertEquals(driver.findElement(By.xpath("//li//span")).getText(), "Invalid login or password.");
	}
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}	