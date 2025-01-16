package rahulshettyacademy.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.TestComponents.Retry;
import rahulshettyacademy.pageObjects.CartPage;
import rahulshettyacademy.pageObjects.CheckoutPage;
import rahulshettyacademy.pageObjects.ConfirmationPage;
import rahulshettyacademy.pageObjects.ProductCatalogue;
//How to handle multiple test cases
//Create new error validation test as per frame
public class ErrorValidationsTest extends BaseTest
{
															  //retryAnalyzer=Retry.class)-->if accidentally if test fails then whatever logic is retrun in Retry class it will follow those rules to re run
	@Test(groups= {"ErrorHandling"},retryAnalyzer=Retry.class)//test run using groups handling.jekhane ai annotation ta dewa hobe sei test gulo sudhu run hbe but
	public void loginErrorValidation() throws IOException, InterruptedException
	{
		String productName ="ADIDAS ORIGINAL";
		ProductCatalogue productCatalogue = landingPage.loginApplication("mallikkkk@gmail.com","Mallick@321");
		Assert.assertEquals("Incorrect email password.",landingPage.getErrorMessage());
	
	}

	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException
	{
		String productName ="ADIDAS ORIGINAL";
		ProductCatalogue productCatalogue = landingPage.loginApplication("maniruddin@gmail.com","Maniruddin@123");
		
		List<WebElement> product =productCatalogue.getProductList();

		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
	
		Boolean match = cartPage.VerifyProductDisplay("ADIDAS ORIGINALlll");
		Assert.assertFalse(match);
		
	}


}
