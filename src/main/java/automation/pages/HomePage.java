package automation.pages;

import automation.drivers.DriverSingleton;
import io.cucumber.java.en.Then;
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

public class HomePage implements BurgerMenu{

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

    @FindBy(id = "react-burger-menu-btn")
    private WebElement burgerMenuButton;

    @FindBy(id = "inventory_sidebar_link")
    private WebElement allItemsLink;

    @FindBy(id = "about_sidebar_link")
    private WebElement aboutLink;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(id = "reset_sidebar_link")
    private WebElement resetLink;

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

    public void selectSortingType(String orderType){
        WebElement sortingDropdown = driver.findElement(By.cssSelector("#header_container > div.header_secondary_container > div > span > select"));
        new Select(sortingDropdown).selectByVisibleText(orderType);
    }

    public List<String> createListProductsByName(){
        //Crea una lista dei nomi dei prodotti
        List<String> originalProductNames = driver.findElements(By.cssSelector(".inventory_item_name"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        return originalProductNames;
    }

    public List<String> productAscendingSorting(){
        List<String> sortedProductNames = driver.findElements(By.cssSelector(".inventory_item_name"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        return sortedProductNames;
    }

    public List<String> productDescendingSorting(){
        List<String> sortedProductNames = driver.findElements(By.cssSelector(".inventory_item_name"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        return sortedProductNames;
    }

    public List<Double> createListProductsByPrice(){
        return driver.findElements(By.cssSelector(".inventory_item_price"))
                .stream()
                .map(element -> element.getText().replace("$", "").trim())
                .map(Double::parseDouble)
                .collect(Collectors.toList());
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

    public void proceedToCheckOut() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.TIMEOUT));
        wait.until(ExpectedConditions.elementToBeClickable(cartButton));
        cartButton.click();
    }

    @Override
    public void viewBurgerMenu(){
        burgerMenuButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.TIMEOUT));
        wait.until(ExpectedConditions.elementToBeClickable(allItemsLink));
        wait.until(ExpectedConditions.elementToBeClickable(aboutLink));
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
        wait.until(ExpectedConditions.elementToBeClickable(resetLink));
    }

    @Override
    public void clickAllItemsLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.TIMEOUT));
        wait.until(ExpectedConditions.elementToBeClickable(allItemsLink));
        allItemsLink.click();
    }

    @Override
    public void clickAboutLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.TIMEOUT));
        wait.until(ExpectedConditions.elementToBeClickable(cartButton));
        aboutLink.click();
    }

    @Override
    public void clickLogout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.TIMEOUT));
        wait.until(ExpectedConditions.elementToBeClickable(cartButton));
        logoutLink.click();
    }

    public List<WebElement> itemIsAvailable(){
        List<WebElement> addButtons = driver.findElements(By.cssSelector(".btn btn_primary btn_small btn_inventory"));
        return addButtons;
    }

    @Override
    public void resetAppState() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.TIMEOUT));
        wait.until(ExpectedConditions.elementToBeClickable(cartButton));
        resetLink.click();
    }

}