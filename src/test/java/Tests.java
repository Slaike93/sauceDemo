import drivers.DriverSingleton;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;
import utils.Constants;
import utils.FrameworkProperties;
import utils.Utils;

import java.time.Duration;

import static junit.framework.Assert.*;

public class Tests{

    static FrameworkProperties frameworkProperties;
    static CartPage cartPage;
    static CheckOutCompletePage checkOutCompletePage;
    static CheckOutOverviewPage checkOutOverviewPage;
    static CheckOutYourInformationPage checkOutYourInformationPage;
    static HomePage homePage;
    static LoginInPage loginInPage;
    static WebDriver driver;

    @BeforeClass
    public static void initializeObjects(){
        frameworkProperties = new FrameworkProperties();
        DriverSingleton.getInstance((frameworkProperties.getProperty(Constants.BROWSER)));
        driver = DriverSingleton.getDriver();
        loginInPage = new LoginInPage();
        homePage = new HomePage();
        cartPage = new CartPage();
        checkOutOverviewPage = new CheckOutOverviewPage();
        checkOutYourInformationPage = new CheckOutYourInformationPage();
        checkOutCompletePage = new CheckOutCompletePage();
    }

    @Test
    public void testingAutentication(){
        driver.get(Constants.URL);
        loginInPage.logIn(frameworkProperties.getProperty(Constants.STANDARD_USER), frameworkProperties.getProperty(Constants.PASSWORD));
        assertEquals(frameworkProperties.getProperty(Constants.HOME_PAGE_TITLE), homePage.getTitle());
    }

    @Test
    public void testWithLockedUser(){
        driver.get(Constants.URL);
        loginInPage.logIn(frameworkProperties.getProperty(Constants.LOCKED_OUT_USER), frameworkProperties.getProperty(Constants.PASSWORD));
        assertEquals(frameworkProperties.getProperty(Constants.LOCKED_OUT_MESSAGE), loginInPage.getErrorMessage());
    }

    @Test
    public void testWithWrongCredentials(){
        driver.get(Constants.URL);
        loginInPage.logIn(frameworkProperties.getProperty(Constants.WRONG_CREDENTIAL), frameworkProperties.getProperty(Constants.PASSWORD));
        assertEquals(frameworkProperties.getProperty(Constants.WRONG_CREDENTIAL_MESSAGE), loginInPage.getErrorMessage());
    }

    //Test di aggiunta elemento al carrello
    @Test
    public void testAddToCart(){
        driver.get(Constants.URL);
        loginInPage.logIn(frameworkProperties.getProperty(Constants.STANDARD_USER), frameworkProperties.getProperty(Constants.PASSWORD));
        int initialCount = homePage.getCartItemCount();
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        int newCount = homePage.getCartItemCount();
        assertTrue("No elements added to cart", newCount > initialCount);
    }

    //Test per effettuare l'ordine e tornare alla home page come conferma del test
    @Test
    public void checkOutConfirmed(){
        //Accesso
        driver.get(Constants.URL);
        loginInPage.logIn(frameworkProperties.getProperty(Constants.STANDARD_USER), frameworkProperties.getProperty(Constants.PASSWORD));
        //Aggiunta al carrello
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        //Vai al carrello
        driver.findElement(By.cssSelector("#shopping_cart_container > a")).click();
        //Vai al checkout
        cartPage.proceedToCheckOut();
        //Inserisci informazioni personali
        checkOutYourInformationPage.InsertYourInformation();
        //Riepilogo e invio ordine
        checkOutOverviewPage.completeCheckOut();
        //Conferma e torna alla home
        checkOutCompletePage.returnHomePage();
        //Test
        assertTrue("Did not go back to the home page", driver.findElement(By.cssSelector("#header_container > div.header_secondary_container > span")).isDisplayed());
    }

    @AfterClass
    public static void closeObjects(){
        driver.close();
    }
}
