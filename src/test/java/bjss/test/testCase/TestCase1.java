package bjss.test.testCase;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import Utilities.ExtentFactory;
import Utilities.Screenshots;
import bjss.test.browsers.Chrome;
import bjss.test.pages.Menu;
import bjss.test.pages.Order;
import bjss.test.pages.Signin;
import bjss.test.pages.Woman;




public class TestCase1 {
	WebDriver driver;
	ExtentReports report;
	ExtentTest test;
	
	@BeforeTest
	public void before(){
		report = ExtentFactory.getInstance();
		test = report.startTest("Test 1", "to cover the steps from the first test");
		driver = Chrome.setup();
		driver.get("http://automationpractice.com/index.php?controller=authentication&back=my-account");
		Signin signin = new Signin(driver,test);	
		signin.signin("TestAutomation@BJSSTest.com","BJSSTest");
		//TestAutomation@BJSSTest.com	
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);// wait for the web page to load
	}
	
	@Test
	public void test1() throws InterruptedException{
		Thread.sleep(2000);
		Menu menu = new Menu(driver,test);
		menu.clickMenuOptionWoman(); 
		Thread.sleep(1000);
		Woman woman = new Woman(driver,test);
		woman.clickItem1(); 
		Thread.sleep(1000);
		woman.itemQuickView().selectSize("L"); 
		Thread.sleep(1000);
		woman.itemQuickView().addToCart(); 
		Thread.sleep(3000);
		woman.itemQuickView().continueShopping(); 
		Thread.sleep(2000);
		woman.clickItem2(); 
		Thread.sleep(2000);	
		woman.itemQuickView().addToCart(); // 
		Thread.sleep(3000);		
		woman.itemQuickView().checkout();;
		Thread.sleep(1000);
		Order order = new Order(driver,test);
		order.summary().checkItem1Size("L");
		order.summary().checkItem2Size("S");
		order.summary().checkItem1Price("16.51");
		order.summary().checkItem2Price("27.00");
		order.summary().checkTotalProductsPrice();
		order.summary().checkFinalTotal();
		order.summary().clickProccedToCheckOut();
		order.address().clickProccedToCheckout();	
		order.shipping().clickTandC();
		order.shipping().clickProccedToCheckout();
		order.payment().clickPayByWire();
		order.payment().clickConfirmOrder();
		menu.clickSignOut();
	}
	
	@AfterMethod
	public void tearDown(ITestResult testResults) throws IOException{
		if(testResults.getStatus() == ITestResult.FAILURE){
			String path = Screenshots.takeScreenshots(driver, testResults.getName());
			String imagePath = test.addScreenCapture(path);
			test.log(LogStatus.FAIL, "the test has failed", imagePath);
		}
		report.endTest(test);
		report.flush();
		}
	
	
	@AfterTest
	public void afterTest(){
		driver.close();
		driver.quit();
	}
	
}
