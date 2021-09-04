package webdriver;

import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Button_Checkbox {
	WebDriver driver;
	Random rand;
	String projectPath = System.getProperty("user.dir");

	JavascriptExecutor jsExecutor;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();

		jsExecutor = (JavascriptExecutor) driver;

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);

	}

//	@Test
	public void TC_01_Button() throws InterruptedException {
		driver.get("https://www.fahasa.com/customer/account/create");
		driver.findElement(By.className("fhs_top_account_login_button")).click();
		driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();

		Assert.assertFalse(driver.findElement(By.cssSelector("button.fhs-btn-login")).isEnabled());
		driver.findElement(By.xpath("//input[@id='login_username']")).sendKeys("0938999888");
		driver.findElement(By.xpath("//input[@id='login_password']")).sendKeys("//input[@id='login_password']");

		Assert.assertTrue(driver.findElement(By.cssSelector("button.fhs-btn-login")).isEnabled());

		String colorOfLoginButton = driver.findElement(By.cssSelector("button.fhs-btn-login"))
				.getCssValue("background-color");
		System.out.println(colorOfLoginButton);
		String colorToHex = Color.fromString(colorOfLoginButton).asHex().toUpperCase();

		System.out.println(colorToHex);
		Assert.assertEquals("#C92127", colorToHex);

		driver.navigate().refresh();
		driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();

		jsExecutor.executeScript("arguments[0].removeAttribute('disabled')",
				driver.findElement(By.cssSelector("button.fhs-btn-login")));
		Assert.assertTrue(driver.findElement(By.cssSelector("button.fhs-btn-login")).isEnabled());
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.cssSelector("li.popup-login-tab-login")));

		driver.findElement(By.cssSelector("button.fhs-btn-login")).click();
		sleepInSecond(5);
//		jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.cssSelector("button.fhs-btn-login")));
//		sleepInSecond(5);
		Assert.assertEquals(driver
				.findElement(By.xpath("//input[@id='login_username']/parent::div/following-sibling::div")).getText(),
				"Thông tin này không thể để trống");

		Assert.assertEquals(driver
				.findElement(By.xpath("//input[@id='login_password']/parent::div/following-sibling::div")).getText(),
				"Thông tin này không thể để trống");
	}

//	@Test
	public void TC_02_Default_Checkbox_RadioButton() throws InterruptedException {
		driver.get("http://demos.telerik.com/kendo-ui/styling/checkboxes");
		sleepInSecond(3);
//		driver.findElement(By.cssSelector("button#onetrust-accept-btn-handler")).click();
		clickAcceptCookies();
		sleepInSecond(3);
//		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//label[contains(text(), 'Dual-zone')]")));
		driver.findElement(By.xpath("//label[contains(text(), 'Dual-zone')]/parent::li/input")).click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//label[contains(text(), 'Dual-zone')]/parent::li/input")).isSelected());
//		
		driver.findElement(By.xpath("//label[contains(text(), 'Dual-zone')]")).click();
		Assert.assertFalse(
				driver.findElement(By.xpath("//label[contains(text(), 'Dual-zone')]/parent::li/input")).isSelected());

		// Radio handling
		driver.get("http://demos.telerik.com/kendo-ui/styling/radios");
//		clickAcceptCookies();
//		driver.findElement(By.xpath("//label[contains(text(),'2.0 Petrol, 147kW')]/parent::li/input")).click();
		clickRadioButton(By.xpath("//label[contains(text(),'2.0 Petrol, 147kW')]/parent::li/input"));

	}

//	@Test
	public void TC_03_RadioButton() throws InterruptedException {
		driver.get("https://material.angular.io/components/radio/examples");
////		driver.findElement(By.xpath("//input[@value='Summer']/preceding-sibling::span[@class='mat-radio-inner-circle']")).click();
////		clickRadioButton(By.xpath("//input[@value='Summer']/preceding-sibling::span[@class='mat-radio-inner-circle']"));
//		
		clickByJS(By.xpath("//input[@value='Summer']"));
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='Summer']")).isSelected());
//		
		driver.get("https://material.angular.io/components/checkbox/examples");
		By checked = By.xpath("//span[text()='Checked']/preceding-sibling::span/input");
		clickByJS(checked);
		Assert.assertTrue(driver.findElement(checked).isSelected());

		By indeterminate = By.xpath("//span[text()='Indeterminate']/preceding-sibling::span/input");
		clickByJS(indeterminate);
		Assert.assertTrue(driver.findElement(indeterminate).isSelected());
		sleepInSecond(10);

		// Uncheck checkbox
		clickByJS(indeterminate);
		Assert.assertFalse(driver.findElement(indeterminate).isSelected());
	}

//	@Test
	public void TC_04_Custome_Checkbox_RadioButton() {
		driver.get(
				"https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
		WebElement canThoNotChecked = driver
				.findElement(By.xpath("//div[@data-value='Cần Thơ' and @aria-checked='false']"));
		Assert.assertTrue(canThoNotChecked.isDisplayed());
		canThoNotChecked.click();
		Assert.assertTrue(
				driver.findElement(By.xpath("//div[@data-value='Cần Thơ' and @aria-checked='true']")).isDisplayed());
		System.out.println("Elemenent is checked:" + driver.findElement(By.xpath("//div[@data-value='Cần Thơ']")).getAttribute("aria-checked"));
		
		//Check checkbox Quang Ngai
		WebElement quangNgai = driver.findElement(By.xpath("//div[@aria-label='Quảng Ngãi']"));
		Assert.assertEquals(quangNgai.getAttribute("aria-checked"), "false");
//		Assert.assertTrue(driver.findElement(By.xpath("//div[@aria-label='Quảng Ngãi' and @aria-checked='false']")).isDisplayed());
//		driver.findElement(By.xpath("//div[@aria-label='Quảng Ngãi' and @aria-checked='false']")).click();
//		Assert.assertTrue(driver.findElement(By.xpath("//div[@aria-label='Quảng Ngãi' and @aria-checked='true']")).isDisplayed());
		quangNgai.click();
		Assert.assertEquals(quangNgai.getAttribute("aria-checked"), "true");
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

	public void sleepInSecond(int value) throws InterruptedException {
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