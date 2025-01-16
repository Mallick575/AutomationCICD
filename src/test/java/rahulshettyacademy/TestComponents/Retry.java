package rahulshettyacademy.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer//Interface to implement to be able to have a chance to retry a failed test.
{

	int count= 0;
	int maxTry = 1;//who many time to rerun 
	@Override
	public boolean retry(ITestResult result) 
	{
		if(count<maxTry) 
		{
			count++;
			return true;
		}
		return false;
	}

}
