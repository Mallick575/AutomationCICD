package rahulshettyacademy.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageObjects.LandingPage;

public class BaseTest 
{
	public WebDriver driver;
	public LandingPage landingPage;
	public WebDriver initializeDriver() throws IOException
	{
		//properties class
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\rahulshettyacademy\\resources\\GlobalData.properties");
		prop.load(fis);
		String browserName=System.getProperty("browser")!=null ? System.getProperty("browser") :prop.getProperty("browser");//whatever are send from maven these are also treated as system-level variables.browser property you want to read.here we use ternari operator
		 //prop.getProperty("browser");
		if(browserName.contains("chrome"))                //no matter if you give chrome or chrome headless both keywords have chrome inside this 
		{								                  //so first it will go inside this method and then again it will check the browser name if contains headless then it will add argument and make your browser to run in headless mode.if it does not conatain that argument inside the if conditon nomally your browser will execute
			ChromeOptions options = new ChromeOptions();//headless mode 
			WebDriverManager.chromedriver().setup();    //don't used hard code because we are using System.getProperty.if you used hard code then it will not run.
			if(browserName.contains("headless"))         //if this does not have headless then this options will not get set 
			{
				options.addArguments("headless");       //when i add this argument to this options so this options know that one of the setting what we want is to run in headless mode now integate options object to your class by sendin as a parameter
			}
			driver = new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension(1440, 900));//full screen
		}
		
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		
		}
		
		else if(browserName.equalsIgnoreCase("edge"))
		{
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	
	//Json file
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
	{
		//Pass json file it will scan the entire content of your json and convert into one string veriable
		//read json to String
		String jsonContent = FileUtils.readFileToString(new File(filePath) ,StandardCharsets.UTF_8);//encoding formate you are trying to write the string in our case standard enconding formate is StandardCharsets.UTF_8 new there are two argumanet
	
		//String to HashMap and need Jackson Databind dependency
		//readValue method read the value and convert to hashmap
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){
		});
		return data;
	}
	
	//How to Create Screenshot Utility in Base Test class for catching Failed tests
	public String getScreenshot(String testCaseName,WebDriver driver) throws IOException //assuming this driver come from listeners level and handle
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir")+"//reports//" +testCaseName + ".png");
		FileUtils.copyFile(source,file);
		return System.getProperty("user.dir")+"//reports//" +testCaseName + ".png";//where screen shot store in your local system
	}
	
	@BeforeMethod(alwaysRun=true)//if u used alwaysRun=true for every group no matter what so that way for each and every group this will pick and it'll make sure it runs
	public LandingPage launchApplication() throws IOException
	{
		driver=initializeDriver();
	    landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}
	

	@AfterMethod(alwaysRun=true)
	public void tearDown()
	{
		//driver.close();
		driver.quit();
	}


}
