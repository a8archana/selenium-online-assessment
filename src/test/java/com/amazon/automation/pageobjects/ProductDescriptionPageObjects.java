package com.amazon.automation.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ProductDescriptionPageObjects {

    private static final Logger logger = LogManager.getLogger(ProductDescriptionPageObjects.class);
    private WebDriver driver;

    private By product_title = By.id("productTitle");
    private By add_to_cart_button = By.id("add-to-cart-button");
    private By drop_down_select_quantity = By.xpath("//select[@id='quantity']"); 
    private By Proceed_To_checkout = By.xpath("//*[@id='sw-gtc']/span/a");
    private By delete_button = By.xpath("//input[@data-action='delete']");
    private By cart_message = By.xpath("//div[@data-action='delete']/span");
     
  
    public ProductDescriptionPageObjects(WebDriver driver){
        this.driver = driver;
    }

    public void clickOnAddToCartButton(){
        driver.findElement(add_to_cart_button).click();
        logger.info("Add to cart button on product description page is clicked.");
        driver.findElement(Proceed_To_checkout).click();
        logger.info("Go to cart button is clicked.");
    }

    public void selectQuantity(String quantity){
        Select select = new Select(driver.findElement(drop_down_select_quantity));
        select.selectByValue(quantity);
        logger.info("Quantity is selected as : " + quantity);
    }


    public void ValidateProductTileIsCorrectlyDisplayed(){
        if (driver.findElement(product_title).isDisplayed()){
            Assert.assertTrue(true);
            logger.info("Product Title is displayed");
        }else{
            logger.fatal("Product Title is not displayed");
            Assert.fail("Product Title is not displayed");
        }
    }

    public void ValidateAddToCartButtonIsCorrectlyDisplayed(){
        if (driver.findElement(add_to_cart_button).isDisplayed()){
            Assert.assertTrue(true);
            logger.info("Add to Cart Button is displayed");
        }else{
            logger.fatal("Add to Cart Button is not displayed");
            Assert.fail("Add to Cart Button is not displayed");
        }
    }

	public void EmptyCart() {
		 driver.findElement(delete_button).click();
	        logger.info("Delete button is clicked.");
		
	}

	public void CartMessage(String message) {
		String actualMessage = driver.findElement(cart_message).getText();
		String fullMessage;
		if(SearchPageObjects.productName.length()>64) {
			fullMessage = SearchPageObjects.productName.substring(0,64).concat(message);
		}else {
			fullMessage = SearchPageObjects.productName.concat(message);
		}
		
				
				
		Assert.assertEquals(actualMessage, fullMessage);
        logger.info("Verified that correct message is displayed "+fullMessage);
		
	}


}
