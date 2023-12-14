package automation.glue;

import automation.config.AutomationFrameworkConfiguration;
import automation.drivers.DriverSingleton;
import automation.pages.*;
import automation.utils.ConfigurationProperties;
import automation.utils.Constants;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;
import automation.config.AutomationFrameworkConfiguration;

import static org.junit.Assert.assertEquals;

@CucumberContextConfiguration
@ContextConfiguration(classes = AutomationFrameworkConfiguration.class)
public class StepDefinition {
    private WebDriver driver;
    private LoginInPage loginInPage;
    private HomePage homePage;
    private CartPage cartPage;
    private CheckOutYourInformationPage checkOutYourInformationPage;
    private CheckOutOverviewPage checkOutOverviewPage;
    private CheckOutCompletePage checkOutCompletePage;

    @Autowired
    ConfigurationProperties configurationProperties;

    @Before
    public void initializeObjects(){
        DriverSingleton.getInstance(configurationProperties.getBrowser());
        loginInPage = new LoginInPage();
        homePage = new HomePage();
        cartPage = new CartPage();
        checkOutYourInformationPage = new CheckOutYourInformationPage();
        checkOutOverviewPage = new CheckOutOverviewPage();
        checkOutCompletePage = new CheckOutCompletePage();
    }

    @Given("I go to the Website")
    public void i_go_to_the_website(){
        driver = DriverSingleton.getDriver();
        driver.get(Constants.URL);
    }

    //Test login standard user
    @When("I specify my standard user credential and click Login")
    public void I_specify_my_standard_user_credential_and_click_login(){
        loginInPage.logIn(configurationProperties.getStandard_user(), configurationProperties.getPassword());
    }

    //Test login locked out user
    @When("I specify my locked out user's credential and click Login")
    public void I_specify_my_locked_out_users_credential_and_click_Login(){
        loginInPage.logIn(configurationProperties.getLocked_out_user(), configurationProperties.getPassword());
    }

    @When("I enter wrong credentials and click login")
    public void I_enter_wrong_credentials_and_click_login(){
        loginInPage.logIn(configurationProperties.getWrong_credential(), configurationProperties.getPassword());
    }

    @Then("I can log into the home page")
    public void I_can_log_into_the_home_page(){
        assertEquals(configurationProperties.getHome_page_title(), homePage.getTitle());
    }

    @Then("I see the locked out error message")
    public void I_see_the_locked_out_error_message(){
        assertEquals(configurationProperties.getLocked_out_message(), loginInPage.getErrorMessage());
    }

    @Then("I see the wrong credentials error message")
    public void I_see_the_wrong_credentials_error_message(){
        assertEquals(configurationProperties.getWrongCredential_message(), loginInPage.getErrorMessage());
    }

    //Aggiungere un prodotto al carrello
    @When("I add a product to the cart")
    public void I_add_a_product_to_the_cart(){
        homePage.addToCart();
    }

    @Then("the product should be added to the cart")
    public void the_product_should_be_added to_the_cart(){

    }
}
