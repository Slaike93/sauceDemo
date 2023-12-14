package pages;

import drivers.DriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Constants;

import java.time.Duration;
import java.util.List;

public class HomePage {

    private WebDriver driver;
    public HomePage() {
        driver = DriverSingleton.getDriver();
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "#header_container > div.header_secondary_container > span")
    private WebElement productsTitle;

    @FindBy(id = "add-to-cart-sauce-labs-backpack")
    private WebElement addToCartButton;

    @FindBy(css = "#shopping_cart_container > a > span")
    private WebElement numberOfProducts;

    @FindBy(id = "shopping_cart_container")
    private WebElement cartButton;

    public String getTitle() {
        return productsTitle.getText();
    }

    public void addToCart() {
        addToCartButton.click();
        if (numberOfProducts.getText().contains(Constants.CART_ITEMS_NUMBER)) {
            System.out.println("Updated cart");
        } else {
            System.out.println("Cart not updated");
        }
    }

    public int getCartItemCount(){
        List<WebElement> cartItems = driver.findElements(By.cssSelector("#shopping_cart_container > a"));
        if(cartItems.isEmpty() || cartItems.get(0).getText().isEmpty()){
            return 0;
        } else {
            String itemCountText = cartItems.get(0).getText();
            try{
                return Integer.parseInt(itemCountText);
            } catch(NumberFormatException e) {
                return 0;
            }
        }
    }

    public void proceedToCheckOut() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.TIMEOUT));
        wait.until(ExpectedConditions.elementToBeClickable(cartButton));
        cartButton.click();
    }
}
