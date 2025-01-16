package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src/test/java/cucumber",glue="rahulshettyacademy.stepDefinitions",
monochrome = true, tags="@Regression",plugin={"html:target/cucumber.html"})           //Its like @Test.what feature file you run that feature file path provide here.Map them glue attribute to the package of your step definitions then runner understands where it is.monochrome give the result in readable formate.and generate the report html plug in.html report i want where that has to stored give it in key value pairs.key is html and value is target/cucumber.html then folder create you automatically 
public class TestNgTestRunner extends AbstractTestNGCucumberTests  //Inbuild cucumber do not have the power of scanning your TestNg code and default cucumber will not be able to scan your TestNg assertions or your TestNG libraries.then extends AbstractTestNGCucumberTests then you are getting all the capabilites what the parent clas provides
{
	

}
