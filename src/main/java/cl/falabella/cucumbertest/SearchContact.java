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
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SearchContact {
	private String baseUrl = "https://www.linkedin.com";
	private WebDriver driver;
	// private String driverPath = "E:/drivers/chrome29/chromedriver_win32/";
	// static String driverPath = "E:/drivers/IEDriverServer_x64_3.4.0/";
	private String browserInUse;
	
	
	@Given("^Se ejecutaran los escenarios usando el navegador \"([^\"]*)\"$")
	public void se_ejecutaran_los_escenarios_usando_el_navegador(String browser) throws Throwable {
		// abriendo los navegadores chrome, ie
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
			System.setProperty("webdriver.ie.driver", url.getPath());			
			
			driver = new InternetExplorerDriver(capabilities);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			browserInUse = browser;

		} else {
			Assert.fail("DEBE ESPECIFICAR UN NOMBRE DE NAVEGADOR DEFINIDO");
		}
	}

	@Given("^El usuario navega a la pagina de inicio$")
	public void el_usuario_navega_a_la_pagina_de_inicio() throws Throwable {
		// navengando en www.linkedin.com
		if (!browserInUse.equalsIgnoreCase("iexplorer")) {
			driver.navigate().to(baseUrl);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	}

	@Given("^Se introduce el \"([^\"]*)\" y la \"([^\"]*)\"$")
	public void se_introduce_el_y_la(String arg1, String arg2) throws Throwable {
		//Introduce usuario y contrasena
		driver.findElement(By.id("login-email")).clear();
		driver.findElement(By.id("login-email")).sendKeys(arg1);
		driver.findElement(By.id("login-password")).clear();
		driver.findElement(By.id("login-password")).sendKeys(arg2);
		
	}

	@When("^Procede a autenticar$")
	public void procede_a_autenticar() throws Throwable {
		//click boton iniciar sesion
		if(browserInUse.equalsIgnoreCase("chrome"))
		driver.findElement(By.id("login-submit")).click();
		else {
			driver.findElement(By.id("login-password")).sendKeys(Keys.ENTER);
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@When("^Se busca un contacto y se visualiza perfil$")
	public void se_busca_un_contacto_y_se_visualiza_perfil() throws Throwable {
		//buscando un contacto en mi red
		driver.findElement(By.cssSelector("input[class*='ember-text-field ember-view']")).sendKeys("Maydalis Pizarro");	
		driver.findElement(By.className("nav-search-button")).click();			
		if (!browserInUse.equalsIgnoreCase("iexplorer")) {
			driver.findElement(By.cssSelector("a[href*='/in/maydalis-pizarro-744744b1/']")).click();

		} else {
			// fix a problema de ie donde queda a la espera infinita de que carga la pagina cuando realmente ya cargo				
			//driver.findElement(By.cssSelector("a[href*='/in/maydalis-pizarro-744744b1/']")).sendKeys("\n");
			//driver.findElement(By.cssSelector("a[href*='/in/maydalis-pizarro-744744b1/']")).click();
			driver.findElement(By.cssSelector("img[class*='lazy-image loaded']")).click();
		}
		
		
	}

	// caps.setCapability("nativeEvents",false); SmartClientWebDriver driver =
	// new SmartClientIEDriver(caps);
	@Then("^Se debe visualizar perfil del contacto$")
	public void se_debe_visualizar_perfil_del_contacto() throws Throwable {
		//Validación de que se muestra el perfil del contacto
		System.out.println("entrando a se_debe_visualizar_perfil_del_contacto");	
		WebElement nombre = driver.findElement(By.cssSelector("h1[class*='pv-top-card-section__name']"));
		Assert.assertTrue(nombre.getText().equalsIgnoreCase("Maydalis Pizarro"));
	
		driver.close();
		driver.quit();
		System.out.println("PERFIL VISITADO Test Correcto");
	}

		
}
