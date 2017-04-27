package cl.falabella.cucumbertest;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LoginFail {
	private String baseUrl = "https://www.linkedin.com";
	private WebDriver driver;
	// private String driverPath = "E:/drivers/chrome29/chromedriver_win32/";
	// static String driverPath = "E:/drivers/IEDriverServer_x64_3.4.0/";
	private String browserInUse;
	
	@Given("^Se determina el navegador \"([^\"]*)\"$")
	public void se_determina_el_navegador_browser(String browser) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		if (browser.equalsIgnoreCase("chrome")) {
			System.out.println("*******************" + browser);
			System.out.println("launching Chrome browser");
			URL url = ClassLoader.getSystemResource("chromedriver2_29.exe");
			// System.setProperty("webdriver.chrome.driver", driverPath +
			// "chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", url.getPath());
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			browserInUse = browser;
		} else if (browser.equalsIgnoreCase("iexplorer")) {
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "IE");
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, baseUrl);
			capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
			capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
			URL url = ClassLoader.getSystemResource("IEDriverServer.exe");
			// URL url =
			// ClassLoader.getSystemResource("IEDriverServer_x64_3.4.0.exe"); el sendkeys es muy lento		
			System.setProperty("webdriver.ie.driver", url.getPath());
			driver = new InternetExplorerDriver(capabilities);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			browserInUse = browser;

		} else {
			Assert.fail("DEBE ESPECIFICAR UN NOMBRE DE NAVEGADOR DEFINIDO");
		}
	}
	
	@Given("^El usuario navega a la pagina de linkedin$")
	public void el_usuario_navega_a_la_pagina_de_linkedin() throws Throwable {
		// navengando en www.linkedin.com
		if (!browserInUse.equalsIgnoreCase("iexplorer")) {
			driver.navigate().to(baseUrl);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	}

	@When("^Se introduce el \"([^\"]*)\" y la \"([^\"]*)\" no registrados$")
	public void se_introduce_el_y_la_no_registrados(String arg1, String arg2) throws Throwable {
		//Introduce datos de usuarios no registrados
		driver.findElement(By.id("login-email")).clear();
		driver.findElement(By.id("login-email")).sendKeys(arg1);
		driver.findElement(By.id("login-password")).clear();
		driver.findElement(By.id("login-password")).sendKeys(arg2);
	}

	@When("^Se procede a autenticar$")
	public void se_procede_a_autenticar() throws Throwable {
		//click boton iniciar sesion		
		if (browserInUse.equalsIgnoreCase("chrome"))
		   driver.findElement(By.id("login-submit")).click();
		else {
			//via alternativa ya que en IE se queda pegado esperando que cargue el sumbit del formulario de login
		   driver.findElement(By.id("login-password")).sendKeys(Keys.ENTER);
		}
		
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Then("^Se debe mostrar mensaje de error$")
	public void se_debe_mostrar_mensaje_de_error() throws Throwable {
		// Se valida mensaje de error
		WebElement errormsg = null;
		if (browserInUse.equalsIgnoreCase("chrome"))
			errormsg = driver.findElement(By.xpath(".//*[@id='control_gen_1']/p/strong"));
		else {	
			//via alternativa ya que en IE no detecta el xpath
			errormsg = driver.findElement(By.cssSelector("div[class*='alert error']"));	
			System.out.println("########################################## " + errormsg.getText());
			
		}
	
		Assert.assertTrue(errormsg.isDisplayed());
		System.out.println("Test Correcto SE COMPRUEBA QUE SE MUESTRA EL MENSAJE DE ERROR DE VALIDACION DEL LOGIN");
		driver.close();
		driver.quit();
	}



	
}
