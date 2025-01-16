package rahulshettyacademy.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageObjects.CartPage;
import rahulshettyacademy.pageObjects.CheckoutPage;
import rahulshettyacademy.pageObjects.ConfirmationPage;
import rahulshettyacademy.pageObjects.LandingPage;
import rahulshettyacademy.pageObjects.ProductCatalogue;

public class StepDefinitionImp extends BaseTest
{
	public LandingPage landingPage;
	ProductCatalogue productCatalogue;
	ConfirmationPage confirmationPage;
	
	@Given("I landed on Ecommerce Page")//You want to write some code that code we are tying up to this @Given but there are a lot of givens there so which given you want to tie that text information you will place in the brackets and its also static 
	public void I_landed_on_Ecommerce_Page() throws IOException
	{//what ever code write inside here that will be executed when the future file will trigger
		landingPage = launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")//it will dynamically comiing.No matter whatever string you see inside (.+) match it.it wil ignore the string and match and then compare
															//this is regular expression to represent that it is a form of RegX(regular expression)
	public void logged_in_username_and_password(String username, String password)
	{
		productCatalogue =  landingPage.loginApplication(username,password);

	}
	
	 @When("^I add product (.+) to Cart$")
	 public void I_add_product_to_cart(String productName) throws InterruptedException
	 {
		 List<WebElement> product =productCatalogue.getProductList();
		 productCatalogue.addProductToCart(productName);
	 }
	 
	 @When("^Checkout (.+) and submit the order$")//and and when both are use
	 public void checkout_submit_order(String productName) throws InterruptedException
	 {
		 	CartPage cartPage = productCatalogue.goToCartPage();
			Boolean match = cartPage.VerifyProductDisplay(productName);//Validations cannot go in page object files.page object files should only have the code to perform actions but if you are validating something which can make your test case pass or fail that kind of validation should be inside your test case only so that is why no assertions should be written in your page object files.
			Assert.assertTrue(match);
			CheckoutPage checkoutPage = cartPage.goToCheckout();
			checkoutPage.seleCountry("india");
			confirmationPage =checkoutPage.submitOrder();
	 }
	 

     @Then("{string} message is displayed on ConfirmationPage")//(.+) it will work if the data is driven from examples.but if you have the string directly on the step itself ,then another simpler way its string data so use {string}
	 public void message_displayed_confirmationPage(String string)
	 {
    	 String confirmMessage = confirmationPage.getConfirmationMessage();
 		 Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
 		 driver.close();
	 }
     
     @Then("^\"([^\"]*)\" message is displayed$")
     public void message_is_displayed(String string) {
         // Write code here that turns the phrase above into concrete actions
    	Assert.assertEquals(string,landingPage.getErrorMessage());
    	driver.close();
     }

}
