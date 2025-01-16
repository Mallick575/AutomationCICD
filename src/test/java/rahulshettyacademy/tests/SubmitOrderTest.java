package rahulshettyacademy.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageObjects.CartPage;
import rahulshettyacademy.pageObjects.CheckoutPage;
import rahulshettyacademy.pageObjects.ConfirmationPage;
import rahulshettyacademy.pageObjects.LandingPage;
import rahulshettyacademy.pageObjects.OrderPage;
import rahulshettyacademy.pageObjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest
{
	String productName ="ADIDAS ORIGINAL";
	@Test(dataProvider="getData",groups= {"Purchaes"})//dataProvider="getData"->this is use for take data from get data method and group this method only
	public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException
	{
		
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"),input.get("password"));
		
		List<WebElement> product =productCatalogue.getProductList();

		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();
	
		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));//Validations cannot go in page object files.page object files should only have the code to perform actions but if you are validating something which can make your test case pass or fail that kind of validation should be inside your test case only so that is why no assertions should be written in your page object files.
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.seleCountry("india");
		ConfirmationPage confirmationPage =checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}
	//sumitorder and order page will different because if are any error in 2 page i will easyly find 
	//To verify ADIDAS ORIGINAL is displaying in order page
	@Test(dependsOnMethods= {"submitOrder"})
	public void orderHistoryTest() throws InterruptedException
	{
		ProductCatalogue productCatalogue = landingPage.loginApplication("mallick@gmail.com","Mallick@321");
		OrderPage ordersPage = productCatalogue.goToOrderPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
		
	}
	

	
	//Extent reports -

	//data provide email,password,productName using 2 dimention array
	/*@DataProvider
	public Object[][] getData()
	{
		
		return new Object[][] {{"mallick@gmail.com","Mallick@321","ADIDAS ORIGINAL"},{"maniruddin@gmail.com","Maniruddin@123","IPHONE 13 PRO"}};
		
	}
	*/
							//or
	//getData() method once problem there see there are there parameters what you are sending to your test.if towmmow 15 parameter then what you do it look messy so used hash map
	/*@DataProvider
	public Object[][] getData()
	{
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("email", "mallick@gmail.com");//key and value pair
		map.put("password", "Mallick@321");
		map.put("product", "ADIDAS ORIGINAL");
		
		HashMap<String,String> map1 = new HashMap<String,String>();
		map1.put("email", "maniruddin@gmail.com");//key and value pair
		map1.put("password", "Maniruddin@123");
		map1.put("product", "IPHONE 13 PRO");
		
		
		
		return new Object[][] {{map},{map1}};
		
	}*/
	//Read data from Json file
	@DataProvider
	public Object[][] getData() throws IOException
	{
		
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\rahulshettyacademy\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
		
	}

}
