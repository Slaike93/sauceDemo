package automation.glue;

import automation.config.AutomationFrameworkConfiguration;
import automation.drivers.DriverSingleton;
import automation.pages.*;
import automation.utils.ConfigurationProperties;
import automation.utils.Constants;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;

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

    ExtentTest test;
    static ExtentReports report = new ExtentReports("report/TestReport.html");

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
//        TestCases[] tests = TestCases.values();
//        test = report.startTest(tests[Utils.testCount].getTestName());
//        Utils.testCount++;

    }

    @Given("I am in the first page of the website")
    public void I_am_in_the_first_page_of_the_website(){
        driver = DriverSingleton.getDriver();
        driver.get(Constants.URL);
        //test.log(LogStatus.PASS, "Navigating to "+Constants.URL);
    }

    @When("I specify {string} and {string} as credentials")
    public void I_specify_username_and_password_as_credentials(String username, String password){
        loginInPage.logIn(username, password);
    }

    @Then("I should see the {string}")
    public void I_should_see_the(String expectedOutcome){
        switch (expectedOutcome){
            case "I see the home page":
                assertEquals("I am not into the home page", Constants.HOME_PAGE_TITLE, homePage.getTitle());
                break;
            case "I see the locked_out_error message":
                assertEquals(Constants.LOCKED_OUT_MESSAGE, loginInPage.getErrorMessage());
                break;
            case "I see the wrong credentials error message":
                assertEquals(Constants.WRONG_CREDENTIAL_MESSAGE, loginInPage.getErrorMessage());
                break;
            default:
                break;
        }
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
        homePage.clickCartButton();
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
        assertEquals("No items added to the cart", 0, itemCount);
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

//---------------------------------------------------------------------

    //Inizio test menu da home page
    //All items
    @And("I click on the menu icon and see the options")
    public void I_click_on_the_menu_icon_and_see_the_options(){
        homePage.viewBurgerMenu();
    }

    @When("I click on All Items option")
    public void I_click_on_All_Items_option() {
        homePage.clickAllItemsLink();
    }

    @When("I click on About option")
    public void I_click_on_About_option(){
        homePage.clickAboutLink();
    }

    @Then("I am redirected to Sauce Labs page")
    public void I_am_redirected_to_Sauce_Labs_page(){
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
        assertEquals("https://saucelabs.com/", driver.getCurrentUrl());
    }

    @When("I click on Logout option")
    public void I_click_on_Logout_option(){
        homePage.clickLogout();
    }

    @When("I click on Reset App State option")
    public void I_click_on_Reset_App_State_option(){
        homePage.resetAppState();
    }

    @Then("Every product should show {string}")
    public void Every_product_should_show(String expectedButtonText){
        List<WebElement> addToCartButtons = driver.findElements(By.cssSelector(".btn_inventory"));

        for (WebElement button : addToCartButtons) {
            System.out.println(button.getText());
            assertEquals("Not resetted", expectedButtonText, button.getText().trim());
        }
    }

    @And("Cart page is empty")
    public void Cart_page_is_empty(){
        List<WebElement> removedCartItems = driver.findElements(By.cssSelector(".removed_cart_items"));
        assertTrue("There are visible elements on cart page.", !removedCartItems.isEmpty());
    }
//---------------------------------------------------------------------

    //Chiudi l'istanza dopo ogni test
    @After
    public void closeInstance(){
        /*
        report.endTest(test);
        report.flush();
        */
        DriverSingleton.closeObjectInstance();
    }


}