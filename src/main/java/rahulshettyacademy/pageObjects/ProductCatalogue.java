package rahulshettyacademy.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import rahulshettyacademy.AbstractComponents.AbstractComponent;
//Page object model not hold any data it should only focus on element and actions
public class ProductCatalogue extends AbstractComponent
{
	WebDriver driver;
	public ProductCatalogue(WebDriver driver)
	{
		super(driver);
		//initialization
		this.driver=driver;
		PageFactory.initElements(driver, this);//for driver.findElement construction
	}
	//Page factory
	@FindBy(css=".mb-3")
	List<WebElement> products;
	@FindBy(css=".ng-animating")
	WebElement spinner;
	
	
	//its not driver. so you can not write page factory so create By
	By productBy = By.cssSelector(".mb-3");
	By addToCart =By.cssSelector(".card-body button:last-of-type");
	By toastMessage =By.cssSelector("#toast-container");

	public List<WebElement> getProductList()
	{
		waitForElementToAppear(productBy);
		return products;
	}
	

	public WebElement getProductByName(String productName)
	{

		WebElement prod =getProductList().stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		return prod;
		
	}
	
	public void addProductToCart(String productName) throws InterruptedException 
	{
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCart).click();//Can you apply page factory within webelement.findElement? no its not possible
		waitForElementToAppear(toastMessage);//its not driver. so you can not write page factory create By
		waitForElementToDisappear(spinner);
	}
	
}



