package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Topic_07_Default_Dropdown {
	WebDriver driver;
	Random rand;
	String projectPath = System.getProperty("user.dir");
	Select select;
	String firstName, lastName, emailAddress, companyName, day, month, year;
	
	JavascriptExecutor jsExecutor;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		
		jsExecutor = (JavascriptExecutor) driver;

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);

		firstName = "Automation";
		lastName = "FC";
		emailAddress = "Ricky" + generateEmail();
		companyName = "VN Trial and Errors";
		day = "10";
		month = "August";
		year = "1986";
	}

	@Test
	public void TC_01_NopCommerce() {
		driver.get("https://demo.nopcommerce.com/");

		driver.findElement(By.className("ico-register")).click();
		driver.findElement(By.id("FirstName")).sendKeys(firstName);
		driver.findElement(By.id("LastName")).sendKeys(lastName);

		select = new Select(driver.findElement(By.name("DateOfBirthDay")));
		select.selectByVisibleText(day);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), day);

		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		select.selectByVisibleText(month);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), month);

		select = new Select(driver.findElement(By.name("DateOfBirthYear")));
		select.selectByVisibleText(year);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), year);

		driver.findElement(By.id("Email")).sendKeys(emailAddress);
		driver.findElement(By.id("Company")).sendKeys(companyName);

		driver.findElement(By.id("Password")).sendKeys("123456");
		driver.findElement(By.id("ConfirmPassword")).sendKeys("123456");

		clickByJS(By.id("register-button"));
		
//		driver.findElement(By.id("register-button")).click();

		// Verify create successfully
		Assert.assertEquals(driver.findElement(By.className("result")).getText(), "Your registration completed");

		// NEW SCREEN - MY ACCOUNT
		driver.findElement(By.xpath("//a[@class='ico-account']")).click();

		Assert.assertEquals(driver.findElement(By.id("FirstName")).getAttribute("value"), firstName);
		Assert.assertEquals(driver.findElement(By.id("LastName")).getAttribute("value"), lastName);
		Assert.assertEquals(driver.findElement(By.id("Email")).getAttribute("value"), emailAddress);
		Assert.assertEquals(driver.findElement(By.id("Company")).getAttribute("value"), companyName);

		select = new Select(driver.findElement(By.name("DateOfBirthDay")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), day);

		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), month);

		select = new Select(driver.findElement(By.name("DateOfBirthYear")));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), year);

//		Verify so luong options
//		Assert.assertEquals(select.getOptions().size(), 32);
//		
//		//Verify dropdown nay khong chon nhieu items duoc
//		Assert.assertFalse(select.isMultiple());
//		

	}

	@Test
	public void TC_02_() {
		driver.findElement(By.className("ico-logout")).click();
		driver.get("https://demo.nopcommerce.com/");
		driver.findElement(By.className("ico-register")).click();
		select = new Select(driver.findElement(By.name("DateOfBirthMonth")));
		
		List<WebElement> allItems = select.getOptions();
		
		for (WebElement item: allItems) {
		System.out.println(item.getText());
		}
		
	}
	
	public void clickByJS (By by) {
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(by));
	}
	
	public String generateEmail() {
		rand = new Random();
		return rand.nextInt(9999) + "@mail.vn";
	}
	
	

}
