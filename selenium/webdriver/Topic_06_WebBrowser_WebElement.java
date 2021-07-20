package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_WebBrowser_WebElement {

	WebDriver driver;
	String projectPath = System.getProperty("user.dir");

	By emailTextBox = By.id("mail");
	By ageUnder18 = By.id("under_18");
	By educationTextArea = By.id("edu");
	By javaCheckbox = By.id("java");
	By sliderEnabled = By.id("slider-1");

	By passwordTextbox = By.id("password");
	By radioDisabled = By.id("radio-disabled");
	By bioArea = By.id("bio");
	By jobRole3 = By.id("job3");
	By checkboxDisabled = By.id("check-disbaled");
	By sliderDisabled = By.id("slider-2");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
		driver.get("https://automationfc.github.io/basic-form/index.html");
	}

	// @Test
	public void TC_01_isDisplayed() {

		// EMAIL
		if (isElementDisplayed(emailTextBox)) {
			sendKeyToElement(emailTextBox, "Automation FC");
		}
//		if(driver.findElement(By.id("mail")).isDisplayed()) {
//			driver.findElement(By.id("email")).sendKeys("Automation FC");
//			System.out.println("Email box is displayed");			
//		}else {
//			System.out.println("Email box is not displayed");
//		}

		// AGE
		if (isElementDisplayed(ageUnder18)) {
			clickElement(ageUnder18);
		}
//		if(driver.findElement(By.id("under_18")).isDisplayed()) {
//			driver.findElement(By.id("under_18")).click();;
//			System.out.println("Age under 18 radio button is displayed");	
//		}else {
//			System.out.println("Age under 18 radio button is not displayed");
//		}		

		// EDU TEXT AREA
		if (isElementDisplayed(educationTextArea)) {
			sendKeyToElement(educationTextArea, "Automation FC");
		}
//		if (driver.findElement(By.id("edu")).isDisplayed()) {
//			driver.findElement(By.id("edu")).click();
//			;
//			System.out.println("Edu text area is displayed");
//		} else {
//			System.out.println("Edu text area is not displayed");
//		}

	}

	// @Test
	public void TC_02_isEnabled() {
		// Expected to be enabled
		Assert.assertTrue(isElementEnabled(emailTextBox));
		Assert.assertTrue(isElementEnabled(ageUnder18));
		Assert.assertTrue(isElementEnabled(educationTextArea));
		Assert.assertTrue(isElementEnabled(javaCheckbox));
		Assert.assertTrue(isElementEnabled(sliderEnabled));

		// Expected to be disabled
		Assert.assertFalse(isElementEnabled(passwordTextbox));
		Assert.assertFalse(isElementEnabled(radioDisabled));
		Assert.assertFalse(isElementEnabled(bioArea));
		Assert.assertFalse(isElementEnabled(jobRole3));
		Assert.assertFalse(isElementEnabled(checkboxDisabled));
		Assert.assertFalse(isElementEnabled(sliderDisabled));
	}

	// @Test
	public void TC_03_isSelected() {
		driver.navigate().refresh();

		// Verify radio & checkbox are selected
		clickElement(ageUnder18);
		Assert.assertTrue(isElementSelected(ageUnder18));
		clickElement(javaCheckbox);
		Assert.assertTrue(isElementSelected(javaCheckbox));

		// Verify radio for ageUnder18 is still selected
		Assert.assertTrue(isElementSelected(ageUnder18));

		// Verify checkbox for java is de-selected
		clickElement(javaCheckbox);
		Assert.assertFalse(isElementSelected(javaCheckbox));
	}

	@Test
	public void TC_04_combineAll() throws InterruptedException {
		driver.get("https://login.mailchimp.com/signup/");

		By passTextbox = By.id("new_password");
		By lowerCase = By.cssSelector(".lowercase-char.completed");
		By upperCase = By.cssSelector(".uppercase-char.completed");
		By number = By.cssSelector(".number-char.completed");
		By specialChar = By.cssSelector(".special-char.completed");
		By minimum8Char = By.cssSelector("li[class='8-char completed']");

		By passOkMessage = By.cssSelector("div[class$='password-ok']");
		By signUpButton = By.id("create-account");
		By newsletter = By.name("marketing_newsletter");

		driver.findElement(By.id("email")).sendKeys("automationfc@mail.vn");
		driver.findElement(By.id("new_username")).sendKeys("nguyen thi");

		// Send keys & Verify displayed - lowercase
		clearText(passTextbox);
		sendKeyToElement(passTextbox, "aaa");
		Thread.sleep(1000);
		Assert.assertTrue(isElementDisplayed(lowerCase));
		Assert.assertFalse(isElementEnabled(signUpButton));

		// Send keys & Verify displayed - uppercase
		clearText(passTextbox);	
		sendKeyToElement(passTextbox, "AAA");
		Thread.sleep(1000);
		Assert.assertTrue(isElementDisplayed(upperCase));
		Assert.assertFalse(isElementEnabled(signUpButton));

		// Send keys & Verify displayed - number
		clearText(passTextbox);
		sendKeyToElement(passTextbox, "123");
		Thread.sleep(1000);
		Assert.assertTrue(isElementDisplayed(number));
		Assert.assertFalse(isElementEnabled(signUpButton));

		// Send keys & Verify displayed - special character
		clearText(passTextbox);
		sendKeyToElement(passTextbox, "!");
		Thread.sleep(1000);
		Assert.assertTrue(isElementDisplayed(specialChar));
		Assert.assertFalse(isElementEnabled(signUpButton));

		// Send keys & Verify displayed - min 8 characters
		clearText(passTextbox);
		sendKeyToElement(passTextbox, "12345678");
		Thread.sleep(1000);
		Assert.assertTrue(isElementDisplayed(minimum8Char));
		Assert.assertTrue(isElementDisplayed(number));
		Assert.assertFalse(isElementEnabled(signUpButton));

		//Satisfy all pass conditions
		clearText(passTextbox);
		sendKeyToElement(passTextbox, "1aA!5678");
		Thread.sleep(1000);
		Assert.assertFalse(isElementDisplayed(lowerCase));
		Assert.assertFalse(isElementDisplayed(upperCase));
		Assert.assertFalse(isElementDisplayed(number));
		Assert.assertFalse(isElementDisplayed(specialChar));
		Assert.assertFalse(isElementDisplayed(minimum8Char));
		
		Assert.assertTrue(isElementEnabled(signUpButton));
		Assert.assertTrue(isElementDisplayed(passOkMessage));
		
		clickElement(newsletter);
		Assert.assertTrue(isElementSelected(newsletter));
		
	}

	public boolean isElementDisplayed(By by) {
		if (driver.findElement(by).isDisplayed()) {
			System.out.println(by + " is displayed");
			return true;

		} else {
			System.out.println(by + " is not displayed");
			return false;
		}
	}

	public boolean isElementSelected(By by) {
		if (driver.findElement(by).isSelected()) {
			System.out.println(by + " is selected");
			return true;

		} else {
			System.out.println(by + " is not selected");
			return false;
		}
	}

	public boolean isElementEnabled(By by) {
		if (driver.findElement(by).isEnabled()) {
			System.out.println(by + " is enabled");
			return true;

		} else {
			System.out.println(by + " is not enabled");
			return false;
		}
	}

	public void sendKeyToElement(By by, String text) {
		driver.findElement(by).clear();
		;
		driver.findElement(by).sendKeys(text);
	}

	public void clickElement(By by) {
		driver.findElement(by).click();
	}

	public void clearText(By by) {
		driver.findElement(by).clear();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
