package rahulshettyacademy.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import rahulshettyacademy.pageObjects.CartPage;
import rahulshettyacademy.pageObjects.OrderPage;
//reused component
public class AbstractComponent
{
	WebDriver driver;
	
	public AbstractComponent(WebDriver driver) 
	{
		this.driver =driver;
		PageFactory.initElements(driver, this);//for driver.findElement construction
	}
	
	public void waitForElementToAppear(By findBy)//(By.cssSelector(".mb-3")):Return type By
	{
	
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	
	public void waitForWebElementToAppear(WebElement findBy)
	{
	
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	wait.until(ExpectedConditions.visibilityOf(findBy));
	}
	
	public void waitForElementToDisappear(WebElement ele) throws InterruptedException//This not find By this is Web Element because driver .findElement is there
	{
		Thread.sleep(4000);
		//4 seconds _Application
		/*our execution is stopping 5 sec here because we are waiting for that loading to 
		disappear as there as another loaders happening(app a mon kore design kora ache 5 sec wait korbe karon eksthe annk user ai side a ase tai late hoche)
		 in the backed that's way 
		selenium is waiting for them also to get disappear but now if you get rid of 
		that piece and if you put simple just one second to load icon then it will be 
		very fast you just need to do add through declaration*/
		//***atai mainly sab jaigai used hbe app a cost cuting jonno wait korache thread diye
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	    //wait.until(ExpectedConditions.invisibilityOf(ele));

	}
	
	@FindBy(css="[routerlink*='cart']")
	WebElement cartHeader;
	
	@FindBy(css="[routerlink*='myorders']")
	WebElement orderHeader;
	
	public CartPage goToCartPage() throws InterruptedException
	{
	
		cartHeader.click();
		CartPage cartPage = new CartPage(driver);
		return cartPage;
	}
	//order page
	public  OrderPage goToOrderPage() throws InterruptedException
	{
	
		orderHeader.click();
		OrderPage orderPage = new OrderPage(driver);
		return orderPage;
	}
	

}
