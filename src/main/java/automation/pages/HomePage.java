package automation.pages;

import automation.drivers.DriverSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import automation.utils.Constants;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

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

    @FindBy(id = "remove-sauce-labs-backpack")
    private WebElement removeItem;

    @FindBy(css = "#header_container > div.header_secondary_container > div > span > select")
    private WebElement sortingMenu;

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

    public List<String> createListProductsByName(){
        List<String> originalProductNames = driver.findElements(By.cssSelector(".inventory_item_name"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        return originalProductNames;
    }

    public List<String> productAscendingSorting(){
        WebElement sortingDropdown = driver.findElement(By.cssSelector("#header_container > div.header_secondary_container > div > span > select"));
        Select dropdown = new Select(sortingDropdown);
        dropdown.selectByVisibleText("Name (A to Z)");

        List<String> sortedProductNames = driver.findElements(By.cssSelector(".inventory_item_name"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        return sortedProductNames;
    }

    public List<String> productDescendingSorting(){
        WebElement sortingDropdown = driver.findElement(By.cssSelector("#header_container > div.header_secondary_container > div > span > select"));
        Select dropdown = new Select(sortingDropdown);
        dropdown.selectByVisibleText("Name (Z to A)");

        List<String> sortedProductNames = driver.findElements(By.cssSelector(".inventory_item_name"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        return sortedProductNames;
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

    public void removeItem(){
        removeItem.click();
    }

    public void sortProductByNameAscending(){
        Select sortMenu = new Select(sortingMenu);
        sortMenu.selectByVisibleText("Name (A to Z)");
    }

    public void sortProductByNameDescending(){
        Select sortMenu = new Select(sortingMenu);
        sortMenu.selectByVisibleText("Name (Z to A)");
    }

    public void sortProductByPriceAscending(){
        Select sortMenu = new Select(sortingMenu);
        sortMenu.selectByVisibleText("Price (low to high)");
    }

    public void sortProductByPriceDescending(){
        Select sortMenu = new Select(sortingMenu);
        sortMenu.selectByVisibleText("Price (high to low)");
    }

    public void proceedToCheckOut() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.TIMEOUT));
        wait.until(ExpectedConditions.elementToBeClickable(cartButton));
        cartButton.click();
    }
}