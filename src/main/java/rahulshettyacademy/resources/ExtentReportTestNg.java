package rahulshettyacademy.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportTestNg 
{
	public static ExtentReports getReportObject()
	{
		String path = System.getProperty("user.dir")+"//reports/index.html";//Explicity provide the path and store your reports.Create a new folder inside a project through java 
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);//ExtentSparkReporter class expects a path where your report should be created.
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Test Results");
		
		//this class reponsible drive all your reporting execution
		//then attach report main class 
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Maniruddin");
		return extent;
		
	}

}
