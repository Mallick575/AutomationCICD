package rahulshettyacademy.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;
//Page object model not hold any data it should only focus on element and actions
public class LandingPage extends AbstractComponent
{
	WebDriver driver;
	public LandingPage(WebDriver driver)
	{
		super(driver);
		//initialization
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	//WebElement useEmail = driver.findElement(By.id("userEmail"));
	//PageFactory
	@FindBy(id="userEmail")//findby helps define and initialize web elements in Page classes.
	WebElement userEmail;//how this annotation know about driver ->There is one method call initElements which you have to write 1st which will take care of constructing that using driver argument what you send in the method  

	@FindBy(id="userPassword")
	WebElement passwordEle;
	
	@FindBy(id="login")
	WebElement submit;
	
	//this is error validation class pop up class khub tara tari atake inspect korte hbe ata 2to class dekhbe disapper class ta selector hub theke copy korte hbe
	//div[@class='ng-tns-c4-12 ng-star-inserted ng-trigger ng-trigger-flyInOut ngx-toastr toast-error']
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage;
	
	//ACTION METHOD
	public ProductCatalogue loginApplication(String email,String password)
	{
		userEmail.sendKeys(email);
		passwordEle.sendKeys(password);
		submit.click();
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);//after send email and password click it sure ProductCatalogue Class so here we create Object
		return productCatalogue;
	}
	
	//error message
	public String getErrorMessage()
	{
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
	}
	
	public void goTo()
	{
		driver.get("https://rahulshettyacademy.com/client/");
	}
	
	
}



