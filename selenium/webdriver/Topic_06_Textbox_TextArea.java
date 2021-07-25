package webdriver;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_Textbox_TextArea {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	// ELEMENTS
	By emailField = By.name("emailid");
	By submitButton = By.name("btnLogin");

	// NEW/EDIT CUSTOMER
	By nameTextBox = By.name("name");
	By genderTextBox = By.name("gender");
	By dobTextBox = By.name("dob");
	By addTextArea = By.name("addr");
	By cityTextBox = By.name("city");
	By stateTextBox = By.name("state");
	By pinTextBox = By.name("pinno");
	By phoneTextBox = By.name("telephoneno");
	By emailTextBox = By.name("emailid");
	By passTextBox = By.name("password");

	// DATA TEST (NEW CUSTOMER)
	String name, dob, add, city, state, pin, phone;

	// DECLARE DATA TEST (EDIT CUSTOMER)
	String editAdd, editCity, editState, editPin, editPhone, editEmail;

	// STRINGS
	String email, loginPageUrl, userID, password, customerID;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();

		// DATA NEW CUSTOMER
		loginPageUrl = "http://demo.guru99.com/v4";
		email = "John" + generateEmail();
		name = "John Lips";
		dob = "01/02/1986";
		add = "23 An Ton Texas";
		city = "California";
		state = "Hawai";
		pin = "987889";
		phone = "0987609876";

		// DATA EDIT CUSTOMER
		editAdd = "42 ngo tat to";
		editCity = "da nang";
		editState = "vietnam";
		editPin = "333212";
		editPhone = "0988666555";
		editEmail = "James" + generateEmail();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		driver.get(loginPageUrl);
	}

	@Test
	public void TC_01_Register() {
		loginPageUrl = driver.getCurrentUrl();
		driver.findElement(By.xpath("//a[text()='here']")).click();

		sendKeyToElement(emailField, email);
		clickElement(submitButton);

		userID = driver.findElement(By.xpath("//td[contains(text(), 'User ID')]/following-sibling::td")).getText();
		password = driver.findElement(By.xpath("//td[contains(text(), 'Password')]/following-sibling::td")).getText();
	}

	@Test
	public void TC_02_Login() throws InterruptedException {
		driver.get(loginPageUrl);
		By userIDField = By.name("uid");
		sendKeyToElement(userIDField, userID);

		By passwordField = By.name("password");
		sendKeyToElement(passwordField, password);

		clickElement(submitButton);

		Assert.assertEquals(driver.findElement(By.cssSelector("marquee.heading3")).getText(),
				"Welcome To Manager's Page of Guru99 Bank");

	}

	@Test
	public void TC_03_Create_New_User() {
		driver.findElement(By.xpath("//a[text()='New Customer']")).click();
		sendKeyToElement(nameTextBox, name);
		sendKeyToElement(dobTextBox, dob);
		sendKeyToElement(addTextArea, add);
		sendKeyToElement(cityTextBox, city);
		sendKeyToElement(stateTextBox, state);
		sendKeyToElement(pinTextBox, pin);
		sendKeyToElement(phoneTextBox, phone);
		sendKeyToElement(emailTextBox, email);
		sendKeyToElement(passTextBox, password);

		driver.findElement(By.name("sub")).click();

		// verify message tao thanh cong
		Assert.assertEquals(driver.findElement(By.className("heading3")).getText(),
				"Customer Registered Successfully!!!");

		// verify cac thong tin da input
		Assert.assertEquals(
				driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), name);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthday']/following-sibling::td")).getText(),
				dob);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(),
				add);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), city);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(),
				state);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), pin);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(),
				phone);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(),
				email);

		customerID = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
	}

	@Test
	public void TC_04_Update_User() throws InterruptedException {
		driver.findElement(By.xpath("//a[text()='Edit Customer'))")).click();
		Thread.sleep(3000);

		driver.findElement(By.name("cusid")).sendKeys(customerID);
		driver.findElement(By.name("AccSubmit")).click();

		// verify 3 fields are DISABLED
		Assert.assertFalse(driver.findElement(nameTextBox).isEnabled());
		Assert.assertFalse(driver.findElement(genderTextBox).isEnabled());
		Assert.assertFalse(driver.findElement(dobTextBox).isEnabled());

		// VERIFY VALUES
		Assert.assertEquals(driver.findElement(nameTextBox).getAttribute("value"), name);
		Assert.assertEquals(driver.findElement(dobTextBox).getAttribute("value"), dob);
		Assert.assertEquals(driver.findElement(addTextArea).getText(), add);
		Assert.assertEquals(driver.findElement(cityTextBox).getAttribute("value"), city);
		Assert.assertEquals(driver.findElement(stateTextBox).getAttribute("value"), state);
		Assert.assertEquals(driver.findElement(pinTextBox).getAttribute("value"), pin);
		Assert.assertEquals(driver.findElement(phoneTextBox).getAttribute("value"), phone);
		Assert.assertEquals(driver.findElement(emailTextBox).getAttribute("value"), email);

		// EDIT CUSTOMER
		driver.findElement(addTextArea).clear();
		driver.findElement(addTextArea).sendKeys(editAdd);

		driver.findElement(cityTextBox).clear();
		driver.findElement(cityTextBox).sendKeys(editCity);

		driver.findElement(stateTextBox).clear();
		driver.findElement(stateTextBox).sendKeys(editState);

		driver.findElement(pinTextBox).clear();
		driver.findElement(pinTextBox).sendKeys(editPin);

		driver.findElement(phoneTextBox).clear();
		driver.findElement(phoneTextBox).sendKeys(editPhone);

		driver.findElement(emailTextBox).clear();
		driver.findElement(emailTextBox).sendKeys(editEmail);

		driver.findElement(By.name("sub")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector(".heading3")).getText(),
				"Customer details updated successfully");

		// VERIFY VALUES
		Assert.assertEquals(driver.findElement(nameTextBox).getAttribute("value"), name);
		Assert.assertEquals(driver.findElement(dobTextBox).getAttribute("value"), dob);

		Assert.assertEquals(driver.findElement(addTextArea).getText(), editAdd);
		Assert.assertEquals(driver.findElement(cityTextBox).getAttribute("value"), editCity);
		Assert.assertEquals(driver.findElement(stateTextBox).getAttribute("value"), editState);
		Assert.assertEquals(driver.findElement(pinTextBox).getAttribute("value"), editPin);
		Assert.assertEquals(driver.findElement(phoneTextBox).getAttribute("value"), editPhone);
		Assert.assertEquals(driver.findElement(emailTextBox).getAttribute("value"), editEmail);

	}

	// @AfterClass
	public void afterClass() {
		driver.quit();
	}

	public String generateEmail() {
		Random rand = new Random();
		return rand.nextInt(9999) + "@mail.vn";
	}

	public void sendKeyToElement(By element, String text) {
		driver.findElement(element).sendKeys(text);
	}

	public void clickElement(By element) {
		driver.findElement(element).click();
	}

};