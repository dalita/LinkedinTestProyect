package cl.falabella.cucumbertest;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/test/resources/cl/falabella/cucumbertest/searchContact.feature",
				"src/test/resources/cl/falabella/cucumbertest/loginFail.feature"}
		,format = {"pretty", "html:results/cucumber"}
		)
public class TestRunner {

}
