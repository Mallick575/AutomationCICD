package rahulshettyacademy.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args)
	{
		//New commonets are added
		String productName ="ADIDAS ORIGINAL";
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://rahulshettyacademy.com/client/");
		driver.findElement(By.id("userEmail")).sendKeys("mallick@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Mallick@321");
		driver.findElement(By.id("login")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		//this way product to load visible on the page then it will take the list of all products
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

		//.mb-3 cssselector common all product
		List<WebElement> products =driver.findElements(By.cssSelector(".mb-3"));
		
		
		/*So we pushed all our list into stream so it iterates,so every item in product is retrieved 
		and on that product we are again deep-driving only inside that product by appling findElement,bycssSelector b
		where you got the actual test name of the product with getText method if that is equal to Zara Coat 3 that is what
		we are filtering and just return 1st one that way we take findFirst if not return any think return null*/
		WebElement prod =products.stream().filter(product->
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		
		/*The scope of search will be within this 1st product only not go hole page that way not used driver 
		then 1st product there is only one css which will help you to identify it*/
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		//basically you want to wait until that toast is displayed then only you can confirm that product is successfully added to cart
		//using explicit wait that toast message is showing up to the screen
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
	
		//ng-animating-its class name loading 
		//here wait we are waiting until that to get disappear 
		//used driver used because improved performance issue slow if used driver it will speedup
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));

		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		//cart product checking
		List<WebElement> cartProducts=driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean match = cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		Actions a = new Actions(driver);
		//sendKeys allows also two argument one webelement and other what you write
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
		//it will wait the until the popup opens
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		//click india
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
	
		driver.findElement(By.cssSelector(".action__submit ")).click();
		
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		driver.quit();
		
	}
	

}
