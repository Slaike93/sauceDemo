package automation.glue;

import automation.config.AutomationFrameworkConfiguration;
import automation.drivers.DriverSingleton;
import automation.pages.*;
import automation.utils.ConfigurationProperties;
import automation.utils.Constants;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;
import automation.config.AutomationFrameworkConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

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

    //Liste di supporto per il test del sorting
    private List<Double> originalPrices;
    private List<String> originalNames;

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

    //Test with wrong credentials
    @When("I enter wrong credentials and click login")
    public void I_enter_wrong_credentials_and_click_login(){
        loginInPage.logIn(configurationProperties.getWrong_credential(), configurationProperties.getPassword());
    }

    @Then("I am into the home page")
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

    //Test add a product to the cart
    @When("I add a product to the cart")
    public void I_add_a_product_to_the_cart(){
        homePage.addToCart();
    }

    @Then("The product should be added to the cart")
    public void The_product_should_be_added_to_the_cart(){
        int itemCount = homePage.getCartItemCount();
        assertTrue("No items added to the cart", itemCount>0);
    }

    @And("Remove the cart item")
    public void Remove_the_cart_item(){
        homePage.removeItem();
    }

    //Test checkout
    @When("I am in the cart page")
    public void I_am_in_the_cart_page(){
        homePage.proceedToCheckOut();
    }

    @And("Click the checkout button and go to the checkout information page")
    public void Click_the_checkout_button_and_go_to_the_checkout_information_page(){
        cartPage.proceedToCheckOut();
    }

    @Then("Enter the personal info and go to the checkout overview page")
    public void Enter_the_personal_info_and_go_to_the_checkout_overview_page(){
        checkOutYourInformationPage.InsertYourInformation();
    }

    @Then("I am in the Checkout overview page and send the order")
    public void I_am_in_the_Checkout_overview_page_and_send_the_order(){
        checkOutOverviewPage.completeCheckOut();
    }

    @Then("I am in the Checkout complete page and click the back home button")
    public void I_am_in_the_Checkout_complete_page_and_click_the_back_home_button(){
        checkOutCompletePage.returnHomePage();
    }

    @And("The cart is empty")
    public void The_cart_is_empty(){
        int itemCount = homePage.getCartItemCount();
        assertTrue("No items added to the cart", itemCount==0);
    }

    //Test ordinamento prodotti A-Z
    @When("I select ascending sorting by name")
    public void I_select_ascending_sorting_by_name(){
        List<String> originalNames = homePage.createListProductsByName();
        this.originalNames = originalNames;
        homePage.selectSortingType("Name (A to Z)");
    }

    @Then("I see the products sorted by name in ascending order")
    public void I_see_the_products_sorted_by_name_in_ascending_order(){
        List<String> sortedProductNames = homePage.productAscendingSorting();

        assertEquals("Products are not sorted in ascending order by name",
                this.originalNames.stream().sorted().collect(Collectors.toList()),
                sortedProductNames);
    }

    //Test ordinamento prodotti Z-A
    @When("I select descending sorting by name")
    public void I_select_descending_sorting_by_name(){
        List<String> originalNames = homePage.createListProductsByName();
        this.originalNames = originalNames;
        homePage.selectSortingType("Name (Z to A)");
    }

    @Then("I see the products sorted by name in descending order")
    public void I_see_the_products_sorted_by_name_in_descending_order(){
        List<String> sortedProductNames = homePage.productDescendingSorting();

        assertEquals("Products are not sorted in descending order by name",
                this.originalNames.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList()),
                sortedProductNames);
    }

    //Test ordinamento prodotti per prezzo low-high
    @When("I select ascending sorting by price")
    public void I_select_ascending_sorting_by_price() {
        List<Double> originalPrices = homePage.createListProductsByPrice();
        homePage.selectSortingType("Price (low to high)");
        this.originalPrices = originalPrices;
    }

    @Then("I see the products sorted by price in ascending order")
    public void I_see_the_products_sorted_by_price_in_ascending_order() {
        List<Double> expectedPrices = new ArrayList<>(this.originalPrices);
        Collections.sort(expectedPrices);

        List<Double> sortedPrices = homePage.createListProductsByPrice();
        assertEquals("Prices not sorted in ascending order",
                expectedPrices, sortedPrices);
    }

    //Test ordinamento prodotti per prezzo high-low
    @When("I select descending sorting by price")
    public void I_select_descending_sorting_by_price(){
        List<Double> originalPrices = homePage.createListProductsByPrice();
        this.originalPrices = originalPrices;
        homePage.selectSortingType("Price (high to low)");
    }

    @Then("I see the products sorted by price in descending order")
    public void I_see_the_products_sorted_by_price_in_descending_order(){
        List<Double> expectedPrices = new ArrayList<>(this.originalPrices);
        Collections.sort(expectedPrices, Collections.reverseOrder());

        List<Double> sortedPrices = homePage.createListProductsByPrice();
        assertEquals("Prices not sorted in descending order",
                expectedPrices, sortedPrices);
    }


    //Chiudi l'istanza dopo ogni test
    @After
    public void closeInstance(){
        DriverSingleton.closeObjectInstance();
    }

}