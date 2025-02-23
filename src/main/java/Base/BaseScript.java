package Base;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
public class BaseScript {
	
	

	public static ExtentTest test1;	
	public static ExtentTest test2;
	public static ExtentTest test3;
	public static ExtentTest test4;
	public static ExtentTest test5;
	public static ExtentReports report;
	public static ExtentReporter htmlReporter;
	public static String getCurrentDateTime()
	{
		DateFormat customFormat=new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
		Date currentDate = new Date();
		return customFormat.format(currentDate);
		
	}
	@BeforeSuite
	public static void startTest()
	{		
		report = new ExtentReports(System.getProperty("user.dir")+"/YahooFinanceTestResults_"+getCurrentDateTime()+".html");
		test1 = report.startTest("Yahoo Finance Webpage Test1","Purpose of the test is to check stock name, company, price and volume");	
		test2 = report.startTest("Yahoo Finance Webpage Test2","Purpose of the test is to check if webpage title mismatch");
		test3 = report.startTest("Yahoo Finance Webpage Test3","Purpose of the test is to check if stock name mismatch");
		test4 = report.startTest("Yahoo Finance Webpage Test4","Purpose of the test is to check invalid xpath element exception");
		test5 = report.startTest("Yahoo Finance Webpage Test5","Purpose of the test is to check wedriver exception when invalid URL is given");
	}
	@Test(priority=0)
	public void test1() throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Eclipse\\SeleniumFramework\\drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();		
		try {
		String title = "Yahoo Finance - Stock Market Live, Quotes, Business & Finance News";
		String expectedStock ="TSLA";
		String expectedLabel ="Tesla, Inc. (TSLA)";
		
		driver.get("https://finance.yahoo.com/");
		//maximize the window 
		driver.manage().window().maximize();
		try {
			this.takeSnapShot(driver, "C:\\Eclipse\\SeleniumFramework\\screenshots\\test01_"+getCurrentDateTime()+".png") ;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(driver.getTitle().equals(title))
		{
			test1.log(LogStatus.PASS, "Test Title Passed");			
		}
		else
		{
			test1.log(LogStatus.FAIL, "Test Title Failed");
		}
		Thread.sleep(1000);
		try {
		driver.findElement(By.xpath("//input[@id='ybar-sbq']")).sendKeys("TSLA");
		} catch(NoSuchElementException e)
		{
			System.out.println("Handled No Such Element Exception");			
		}
		Thread.sleep(1000);
		test1.log(LogStatus.INFO, "TSLA is Typed");
		try {
			this.takeSnapShot(driver, "C:\\Eclipse\\SeleniumFramework\\screenshots\\test01_"+getCurrentDateTime()+".png") ;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Actions act = new Actions(driver);
	    act.sendKeys(Keys.DOWN).perform();
	    Thread.sleep(1000);	    
	    act.sendKeys(Keys.UP).perform();
	    Thread.sleep(1000);	    
		final Actions actions = new Actions(driver);
	    actions.moveToElement(driver.findElement(By.xpath("//div[normalize-space()='TSLA']"))).getClass();
	    WebElement element = driver.findElement(By.xpath("//div[normalize-space()='TSLA']"));
	    String t = element.getAttribute("textContent");
	    System.out.println(t);
	    test1.log(LogStatus.PASS, "TSLA is Captured and Asserted =>  " + t);
	    Assert.assertEquals(t, expectedStock);	    	   
		Thread.sleep(1000);
		try {
		actions.moveToElement(driver.findElement(By.xpath("//div[normalize-space()='TSLA']"))).click().perform();	   
		} catch (ElementNotInteractableException e)
		{
			System.out.println("Handled ElementNotInteractableException");			
		}
		Thread.sleep(1000);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='yf-xxbei9']")));
		WebElement element1 = driver.findElement(By.xpath("//h1[@class='yf-xxbei9']"));
	    String landingPageLabel = element1.getAttribute("textContent");
	    System.out.println(landingPageLabel);
	    Assert.assertEquals(landingPageLabel, expectedLabel);	
	    test1.log(LogStatus.PASS, "TSLA Inc. is Captured and Asserted => " + landingPageLabel);
	    Thread.sleep(1000);
	    WebElement element2 = driver.findElement(By.xpath("//span[contains(@class,'yf-ipw1h0')][normalize-space()='337.80']"));
	    String StockCost = element2.getAttribute("textContent");
	    System.out.println(StockCost);
	    test1.log(LogStatus.PASS, "TSLA Stock Price is Captured =>  " + StockCost);
	    String correctstring[] = StockCost.split("\\.");
	    System.out.println(correctstring[0]);
	    System.out.println(correctstring[1]);
	    String actValue = correctstring[0];
	    int actCost = Integer.parseInt(actValue);
	    int minCost = 200;
	    Assert.assertTrue(actCost > minCost, "Actual Value is greater than the min cost 200");	 
	    test1.log(LogStatus.INFO,"stockCost is greater than 200");
	    Thread.sleep(1000);
	    WebElement element3 = driver.findElement(By.xpath("//*[@title and @title='Previous Close']"));
	    String PreviousClose = element3.getAttribute("textContent");	    
	    System.out.println(PreviousClose);		    
	    Thread.sleep(1000);
	    WebElement element4 = driver.findElement(By.xpath("//*[@data-field='regularMarketPreviousClose']"));
	    String PreviousCloseValue = element4.getAttribute("textContent");	    
	    System.out.println(PreviousCloseValue);
	    test1.log(LogStatus.PASS, "TSLA Stock Previous Close Price is Captured =>  " + PreviousCloseValue);
	    Thread.sleep(1000);
	    WebElement element5 = driver.findElement(By.xpath("//*[@title and @title='Volume']"));
	    String Volume = element5.getAttribute("textContent");	    
	    System.out.println(Volume);	    
	    Thread.sleep(1000);
	    WebElement element6 = driver.findElement(By.xpath("//*[@data-field='regularMarketVolume']"));
	    try {
			this.takeSnapShot(driver, "C:\\Eclipse\\SeleniumFramework\\screenshots\\test01_"+getCurrentDateTime()+".png") ;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    String VolumeValue = element6.getAttribute("textContent");	    
	    System.out.println(VolumeValue);
	    test1.log(LogStatus.PASS, "TSLA Stock Volume is Captured =>  " + VolumeValue);
	    Thread.sleep(1000);
	    driver.quit();
		} catch(InterruptedException e) {
			System.out.println("Handled InterruptedException");					
		}		
	}
	@Test(priority=1)
	public void test2() throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Eclipse\\SeleniumFramework\\drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		try {
		String title = "Yahoo Finance - Stock Market Live, Quotes, Business  Finance News";				
		driver.get("https://finance.yahoo.com/");
		//maximize the window 
		driver.manage().window().maximize();
		try {
			this.takeSnapShot(driver, "C:\\Eclipse\\SeleniumFramework\\screenshots\\test02_"+getCurrentDateTime()+".png") ;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(driver.getTitle().equals(title))
		{
			test2.log(LogStatus.PASS, "Test Title Passed");			
		}
		else
		{
			test2.log(LogStatus.FAIL, "Test Title Failed");
		}
		driver.quit();
		Thread.sleep(1000);
		} catch(InterruptedException e) {
			System.out.println("Handled InterruptedException");			
		}		
	}
	@Test(priority=2)
	public void test3() throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Eclipse\\SeleniumFramework\\drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();	
		try {
		String title = "Yahoo Finance - Stock Market Live, Quotes, Business & Finance News";
		String expectedStock_test3 ="TSLB";				
		driver.get("https://finance.yahoo.com/");
		//maximize the window 
		driver.manage().window().maximize();
		try {
			this.takeSnapShot(driver, "C:\\Eclipse\\SeleniumFramework\\screenshots\\test03_"+getCurrentDateTime()+".png") ;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(driver.getTitle().equals(title))
		{
			test3.log(LogStatus.PASS, "Test Title Passed");			
		}
		else
		{
			test3.log(LogStatus.FAIL, "Test Title Failed");
		}
		Thread.sleep(1000);
		try {
		driver.findElement(By.xpath("//input[@id='ybar-sbq']")).sendKeys("TSLA");
		} catch(NoSuchElementException e)
		{
			System.out.println("Handled No Such Element Exception");			
		}
		Thread.sleep(1000);
		test3.log(LogStatus.INFO, "TSLA is Typed");
		try {
			this.takeSnapShot(driver, "C:\\Eclipse\\SeleniumFramework\\screenshots\\test03_"+getCurrentDateTime()+".png") ;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Actions act = new Actions(driver);
	    act.sendKeys(Keys.DOWN).perform();
	    Thread.sleep(1000);	    
	    act.sendKeys(Keys.UP).perform();
	    Thread.sleep(1000);	    
		final Actions actions = new Actions(driver);
	    actions.moveToElement(driver.findElement(By.xpath("//div[normalize-space()='TSLA']"))).getClass();
	    WebElement element = driver.findElement(By.xpath("//div[normalize-space()='TSLA']"));
	    String t = element.getAttribute("textContent");
	    System.out.println(t);	    
	    Assert.assertNotEquals(t, expectedStock_test3);
	    if(t!=expectedStock_test3)
		{
			test3.log(LogStatus.FAIL, "Assertion Failed");			
		}
		else
		{
			test3.log(LogStatus.PASS, "Assertion Passed");
		}
	    test3.log(LogStatus.PASS, "TSLA is Captured and Asserted =>  " + t +","+expectedStock_test3);
		Thread.sleep(1000);
		try {
		actions.moveToElement(driver.findElement(By.xpath("//div[normalize-space()='TSLA']"))).click().perform();	   
		} catch (ElementNotInteractableException e)
		{
			System.out.println("Handled ElementNotInteractableException");			
		}
		Thread.sleep(1000);		
	    driver.quit();
		} catch(InterruptedException e) {
			System.out.println("Handled InterruptedException");			
		}		
	}
	@Test(priority=3)
	public void test4() throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Eclipse\\SeleniumFramework\\drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		try {
		String title = "Yahoo Finance - Stock Market Live, Quotes, Business & Finance News";			
		driver.get("https://finance.yahoo.com/");
		//maximize the window 
		driver.manage().window().maximize();
		try {
			this.takeSnapShot(driver, "C:\\Eclipse\\SeleniumFramework\\screenshots\\test04_"+getCurrentDateTime()+".png") ;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(driver.getTitle().equals(title))
		{
			test4.log(LogStatus.PASS, "Test Title Passed");			
		}
		else
		{
			test4.log(LogStatus.FAIL, "Test Title Failed");
		}
		Thread.sleep(1000);
		try {
		driver.findElement(By.xpath("//input[@id='ybar']")).sendKeys("TSLA");		
		} catch(NoSuchElementException e)
		{
			System.out.println("Handled No Such Element Exception");			
		}
		test4.log(LogStatus.FAIL, "Handled No Such Element Exception");
		Thread.sleep(1000);		
	    driver.quit();
		} catch(InterruptedException e) {
			System.out.println("Handled InterruptedException");			
		}		
	}
	@Test(priority=4)
	public void test5() throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Eclipse\\SeleniumFramework\\drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		try {					
		driver.get("https://finance.yahoo.co/");
		try {
			this.takeSnapShot(driver, "C:\\Eclipse\\SeleniumFramework\\screenshots\\test05_"+getCurrentDateTime()+".png") ;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Thread.sleep(1000);
		driver.quit();			    
		} catch(WebDriverException e) {
			System.out.println("Handled WebDriverException");			
		} finally {
			test5.log(LogStatus.FAIL, "Test Failed with invalid URL");
			driver.quit();	

			}	
	}
	@AfterSuite
	public static void endTest()
	{		
		report.endTest(test1);
		report.endTest(test2);
		report.endTest(test3);
		report.endTest(test4);
		report.endTest(test5);
		report.flush();
	}	
	/**
	* This function will take screenshot
	* @param webdriver
	* @param fileWithPath
	* @throws Exception
	*/
	public void takeSnapShot(WebDriver webdriver,String fileWithPath) throws Exception{
	//Convert web driver object to TakeScreenshot
	TakesScreenshot scrShot =((TakesScreenshot)webdriver);
	//Call getScreenshotAs method to create image file
	File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
	//Move image file to new destination
	File DestFile=new File(fileWithPath);
	//Copy file at destination
	FileUtils.copyFile(SrcFile, DestFile);		
	}
    
}
