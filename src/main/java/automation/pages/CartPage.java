package automation.pages;

import automation.drivers.DriverSingleton;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import automation.utils.Constants;

import java.time.Duration;
import java.util.List;

public class CartPage {
    private WebDriver driver;

    public CartPage() {
        driver = DriverSingleton.getDriver();
        PageFactory.initElements(driver, this);
    }

    //Pulsante checkout
    @FindBy(id = "checkout")
    private WebElement checkOutButton;

    //Titolo del carrello
    @FindBy(css = "#header_container > div.header_secondary_container > span")
    private WebElement yourCartTitle;

    //Andare al checkout
    public void proceedToCheckOut() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.TIMEOUT));
        wait.until(ExpectedConditions.elementToBeClickable(checkOutButton));
        try {
            if (yourCartTitle.getText().equals(Constants.CART_TITLE)){
                System.out.println("Proceed to checkout");
                checkOutButton.click();
            } else {
                System.out.println("Can't continue");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
