package webdriver;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TC_08_Custom_Dropdown {
	WebDriver driver;
	WebDriverWait explicitWait;
	JavascriptExecutor jsExecutor;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		driver = new ChromeDriver();
		explicitWait = new WebDriverWait(driver, 15);
		//Ép kiểu ngầm định: từ kiểu dlieu có range nhỏ hơn lên lớn hơn
//		int price = 156000;
//		float size = price;

		// Ép kiểu tường minh: lớn -> nhỏ
//		short sPrice = (short) price;
		jsExecutor = (JavascriptExecutor) driver;

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_JQuery() {
		driver.get("http://jqueryui.com/resources/demos/selectmenu/default.html");
		selectItetmInCustomDropdown("//span[@id='number-button']","//div[@role='option']","19");
		Assert.assertTrue(driver.findElement(By.xpath("//span[@id='number-button']//span[@class='ui-selectmenu-text' and text()='19']")).isDisplayed());

	}

	@Test
	public void TC_02_React() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		selectItetmInCustomDropdown("//div[@role='listbox']","//div[@role='option']/span","Justen Kitsune");
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='divider text' and text()='Justen Kitsune']")).isDisplayed());

	}

	@Test
	public void TC_03_VueJS() {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		selectItetmInCustomDropdown("//li[@class='dropdown-toggle']","//ul[@class='dropdown-menu']//li","Third Option");
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='dropdown-toggle' and contains(text(), 'Third Option')]")).isDisplayed());
	}

	public void selectItetmInCustomDropdown(String parentXpath, String childXpath, String expectedItem) {
		//click vao 1 element de xo ra het cac element
		driver.findElement(By.xpath(parentXpath)).click();
		
		//chờ cho tất cả các elements đc load
		List<WebElement> allItems = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(childXpath)));
		
		for (WebElement item: allItems) {
			if (item.getText().trim().equals(expectedItem)) {
				if (!item.isDisplayed()) {
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
					sleepInSecond(1);
				} 
				item.click();
				break;
			}
		}
	}
	public void sleepInSecond(long timeoutInSecond) {
		try {
			Thread.sleep(timeoutInSecond * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
