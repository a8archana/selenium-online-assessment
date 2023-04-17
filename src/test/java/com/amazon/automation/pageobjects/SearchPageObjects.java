package com.amazon.automation.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchPageObjects {

    private static final Logger logger = LogManager.getLogger(SearchPageObjects.class);

    private WebDriver driver;
    public static String productName;
    
    private By search_refinement_categories_segment  = By.id("s-refinements");
    private By product_link_list = By.xpath("//*[@data-component-type='s-search-result']//h2/a/span"); 
    private By product_price_list = By.xpath("//*[@data-component-type='s-search-result']//span[@class='a-price-whole']"); 
    
    
    public SearchPageObjects(WebDriver driver){
        this.driver = driver;
    }

    public void ValidateProductSearchIsSuccessfull(){
        if (driver.findElement(search_refinement_categories_segment).isDisplayed()){
            Assert.assertTrue(true);
            logger.info("Search Page is displayed because refinement category is displayed");
        }else{
            logger.fatal("Search Page is not displayed because refinement category is not displayed");
            Assert.fail("Search Page is not displayed because refinement category is not displayed");
        }
    }

    public void searchResultVerification(String text){
        List<WebElement> listOfProducts = driver.findElements(product_link_list);
        logger.info("Number of products searched: " + listOfProducts.size());
        int titleContainingText =0;
        int titleNotContainingText =0;
        for(WebElement element:listOfProducts) {
        	if(element.getText().contains(text)) {
        		titleContainingText++;
        	}else {
        		titleNotContainingText++;
        	}
        }
        
        logger.info("Validated the total List elements that has word: " + text +
                " are"+ titleContainingText + "");
        logger.info("Validated the total List elements that does npt have word: " + text +
                " are"+ titleNotContainingText + "");


       }

    public void lowestPriceItem(){
        List<WebElement> listOfProductsPrice = driver.findElements(product_price_list);
        logger.info("Number of products listing their prices: " + listOfProductsPrice.size());

        ArrayList<Integer> prices=new ArrayList<Integer>();
        
        for(WebElement element:listOfProductsPrice) {
        	int price = Integer. parseInt(element.getText().replace(",", ""));
        	prices.add(price);
        }
        Collections.sort(prices);
        
        int lowestPrice = prices.get(0);
        
        
        productName = driver.findElement(By.xpath("//*[@data-component-type='s-search-result']//span[@class='a-price-whole' and text()='"+lowestPrice+"']//ancestor::div[@class='a-section a-spacing-base']//h2/a/span")).getText();
         
      
        driver.findElement(By.xpath("//*[@data-component-type='s-search-result']//span[@class='a-price-whole' and text()='"+lowestPrice+"']")).click();
        
        logger.info("Clicked on the Link in the product with the List with lowest Price: " + lowestPrice );

      }

}
