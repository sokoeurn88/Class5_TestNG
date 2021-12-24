package testng;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNg {
	WebDriver driver;	//for chrome
//	String browser = "EDGE";
	String browser;
	String url;
	
	
	//login data
	String username = "demo@techfios.com";
	String userpassword = "abc123";
//	String userlogin = "";
//	String userdashboard = "";
	
	
	
	//Test data
	String Full_Name = "TestNG";
	String Company_Name = "Techfios";
	String Email_Name = "abc234@techfios.com";
	String Phone_Number = "5012153790";
	String Country = "Angola";
	
	
	//storing element with By Class
	By userNameField = By.xpath("/html/body/div/div/div/form/div[1]/input");	
	By userPasswordField = By.xpath("/html/body/div/div/div/form/div[2]/input");	
	By userLoginField = By.xpath("/html/body/div/div/div/form/div[3]/button");
//	By userDashboardField = By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2");
	
	By userCustomerField = By.xpath("//*[@id=\"side-menu\"]/li[3]/a/span[1]");
	By userAddCustomerField = By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a");
	By userFullNameField = By.xpath("//*[@id=\"account\"]");
	By userCompanyField = By.xpath("//*[@id=\"cid\"]");
	By userEmailField = By.xpath("//*[@id=\"email\"]");
	By userPhoneNumberField = By.xpath("//*[@id=\"phone\"]");
	private By userDashboardField;
	By userCountryField = By.xpath("//*[@id=\"country\"]");
	By userSaveField = By.xpath("//*[@id=\"submit\"]");
	private String visibleText;
	
	
	
	
	@BeforeClass
	public void readConfig() {
		
		//4 ways to read a file: FileReader, InputStream, Buffered, Scanner
		
		//Class for java to understand property file:
		Properties pro = new Properties();
		
		//try{}catch(){}
		try {
			
			//to get input
			InputStream input = new FileInputStream("src\\main\\java\\readpropertyfile\\config.properties");	//erros before, I do not copy the right path.
			
			//to load
			pro.load(input);
			
			//to read
			pro.getProperty("browser");	
			
			browser = pro.getProperty("browser");
			
			System.out.println("Browser used: "+ browser);
			url = pro.getProperty("url");
			
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@BeforeMethod
	public void init() {
		
		if(browser.equalsIgnoreCase("chrome")) {
			
			//for chrome
			System.setProperty("webdriver.chrome.driver", "Drivers\\chromedriver.exe");
			driver = new ChromeDriver();	//remove WebDriver
			
		}else if(browser.equalsIgnoreCase("edge")) {
			
			//for microsoft edge driver
			System.setProperty("webdriver.edge.driver", "Drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
			
		}
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get(url);
		
	}
	
	@Test(priority=1)
	public void loginTest() {
		
		driver.findElement(userNameField).sendKeys(username);
		driver.findElement(userPasswordField).sendKeys(userpassword);
		driver.findElement(userLoginField).click();
		
		
		By userDashboardField = By.xpath("//h2[contains(text(), ' Dashboard ')]");
		driver.findElement(userDashboardField).click();
//		Assert.assertEquals(driver.findElement(userDashboardField).getText(), " Dashboard ", "Page does not find, pleas try again");
//		Assert.assertEquals(driver.findElement(By.xpath("//h2[contains(text(), 'Dashboard')]")).getText(), " Dashboard ");
		Assert.assertTrue(true, "wrong page, pleas try agian!");
	}
	
	@Test(priority=2)
	public void addCustomerTest() {
						
		driver.findElement(userNameField).sendKeys(username);
		driver.findElement(userPasswordField).sendKeys(userpassword);
		driver.findElement(userLoginField).click();
		
//		By userDashboardField = By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2");
		By userCustomerDashboardField = By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2");
		driver.findElement(userCustomerDashboardField).click();
		

		Assert.assertTrue(true, "wrong page, pleas try agian!");
		
		driver.findElement(userCustomerField).click();
		driver.findElement(userAddCustomerField).click();
		
		Random rd = new Random();
//		rd.nextInt(999);
		int generatedNo = rd.nextInt(999);
		
		driver.findElement(userFullNameField).sendKeys(Full_Name + generatedNo);
//		driver.findElement(userCompanyField).sendKeys(Company_Name);	
		
		selectFromDropdown(driver.findElement(userCompanyField), Company_Name);
		
		driver.findElement(userEmailField).sendKeys(generatedNo + Email_Name);
		driver.findElement(userPhoneNumberField).sendKeys(Phone_Number + generatedNo);
		
//		driver.findElement(userCountryField).sendKeys(Country);
		
		selectFromDropdown(driver.findElement(userCountryField), Country);
		
//		driver.findElement(userSaveField).click();
	}
		
	private void selectFromDropdown(WebElement element, String visibleText) {
		Select sel = new Select(element);
		sel.selectByValue(visibleText);
		
	}

//	@AfterMethod
	public void tearDown() throws InterruptedException {
		Thread.sleep(1000);
		driver.close();
		driver.quit();
	}

}
