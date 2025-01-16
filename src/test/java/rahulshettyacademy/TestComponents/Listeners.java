package rahulshettyacademy.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import rahulshettyacademy.resources.ExtentReportTestNg;

public class Listeners extends BaseTest implements ITestListener
{
	ExtentTest test;
    ExtentReports extent = ExtentReportTestNg.getReportObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();//Thread safe.ExtentTest is an object
  
    
	@Override//Before start any test control will reach this method.then here itself will let's create entry for that test.obviously every test will come here 
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());//result variable holds the information about the test which is going to get executed here we get method name 
		extentTest.set(test);//set will assign a uniqe thread id(ErrorValidationTest)-->it store as a map
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentTest.get().log(Status.PASS,"Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result)
	{
		extentTest.get().fail(result.getThrowable());//get pull the unique id where set is created and pull it.it will give all the error message fail(result.getThrowable().
		
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());//get() return the filed.The instance on which this method was run.
		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		String filePath = null;
		try {
			filePath = getScreenshot(result.getMethod().getMethodName(),driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		extentTest.get().addScreenCaptureFromPath(filePath,result.getMethod().getMethodName());//2nd argument you can send the name of how it has to show in the report
		//step1:take a ScreenShot step 2:Attach to report
		//one file path is taken we are sending that to addScreenCaptureFromPath().this method path from you local system and attach to your extent report 
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override//**********************important******************** 
	public void onFinish(ITestContext context) {
	
		extent.flush();//its execute last before it shows the reports if you missed that it won show reported generated on the screen
	}
	

}
