import drivers.DriverSingleton;
import org.openqa.selenium.WebDriver;
import pages.*;
import utils.Constants;
import utils.FrameworkProperties;

public class Main {
    public static void main(String[] args) {
        FrameworkProperties frameworkProperties = new FrameworkProperties();
        DriverSingleton driverSingleton = DriverSingleton.getInstance(frameworkProperties.getProperty("browser"));
        WebDriver driver = DriverSingleton.getDriver();
        driver.get("https://saucedemo.com/");

        LoginInPage loginInPage = new LoginInPage();

        HomePage homePage = new HomePage();

        CartPage cartPage = new CartPage();

        CheckOutYourInformationPage checkOutYourInformationPage = new CheckOutYourInformationPage();

        CheckOutOverviewPage checkOutOverviewPage = new CheckOutOverviewPage();

        CheckOutCompletePage checkOutCompletePage = new CheckOutCompletePage();

        loginInPage.logIn(Constants.STANDARD_USER, Constants.PASSWORD);

        if(homePage.getTitle().equals("Products")){
            System.out.println("Test passed");
        } else {
            System.out.println("Test failed");
        }

        homePage.addToCart();

        homePage.proceedToCheckOut();

        cartPage.proceedToCheckOut();

        checkOutYourInformationPage.InsertYourInformation();

        checkOutOverviewPage.completeCheckOut();

        checkOutCompletePage.returnHomePage();

        DriverSingleton.closeObjectInstance();

    }
}