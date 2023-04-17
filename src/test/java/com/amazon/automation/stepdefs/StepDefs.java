package com.amazon.automation.stepdefs;

import com.amazon.automation.core.WebDriverFactory;
import com.amazon.automation.pageobjects.*;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class StepDefs {

    //***********************************************************************
    //***********************Logger Init*************************************
    //***********************************************************************
    private static final Logger logger = LogManager.getLogger(StepDefs.class);

    //***********************************************************************
    //***********************Declaration*************************************
    //***********************************************************************

    WebDriver driver;
    String base_url = "https://amazon.in";
    int implicit_wait_timeout_in_sec = 20;
    Scenario scn; // this is set in the @Before method

    //***********************************************************************
    //***********************Page Object Model Declaration*******************
    //***********************************************************************
    ProductValidation productValidation;
    SearchPageObjects searchPageObjects;
    ProductDescriptionPageObjects productDescriptionPageObjects;

    //***********************************************************************
    //***********************HOOKS*******************************************
    //***********************************************************************
    @Before
    public void setUp(Scenario scn) throws Exception {
        this.scn = scn; 
        String browserName = WebDriverFactory.getBrowserName();
        driver = WebDriverFactory.getWebDriverForBrowser(browserName);
        logger.info("Browser invoked.");

        productValidation = new ProductValidation(driver);
        searchPageObjects = new SearchPageObjects(driver);
        productDescriptionPageObjects = new ProductDescriptionPageObjects(driver);

        WebDriverFactory.navigateToTheUrl(base_url);
        scn.log("Browser navigated to URL: " + base_url);

        String expected = "Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in";
        productValidation.validatePageTitleMatch(expected);

        String sdfsf = "{\n" +
                "  \"content\": [\n" +
                "    {\n" +
                "      \"fullName\": \"string\",\n" +
                "      \"userName\": \"string\"\n" +
                "    }\n" +
                "  ],";
    }


    @After(order=1)
    public void cleanUp(){
    	driver.quit();
        scn.log("Browser Closed");
    }

    @After(order=2)
    public void takeScreenShot(Scenario s) {
        if (s.isFailed()) {
            TakesScreenshot scrnShot = (TakesScreenshot)driver;
            byte[] data = scrnShot.getScreenshotAs(OutputType.BYTES);
            scn.attach(data, "image/png","Failed Step Name: " + s.getName());
        }else{
            scn.log("Test case is passed, no screen shot captured");
        }
    }

    //***********************************************************************
    //***********************Step Defs***************************************
    //***********************************************************************

    @Given("User navigated to the home application url")
    public void user_navigated_to_the_home_application_url() {
        WebDriverFactory.navigateToTheUrl(base_url);
        scn.log("Browser navigated to URL: " + base_url);

        String expected = "Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in";
        productValidation.validatePageTitleMatch(expected);
    }

    @When("User Search for product {string}")
    public void user_search_for_product(String productName) {
        productValidation.SetSearchTextBox(productName);
        scn.log("Product Searched: " + productName);
    }

    @When("Validate all search suggestions contain {string} and choose last gel pen option from the suggestions")
    public void search_Suggestion(String productName) {
        productValidation.ValidateSearchResultAndClickLastOption(productName);
        scn.log("Product Searched: " + productName);
    }
    
    @When("Check the search result ensuring every product item has the {string} in its title")
    public void search_result_contain_search_criteria(String productName) {
        searchPageObjects.searchResultVerification(productName);
        scn.log("Listing products having the word: " + productName);
     }

    @When("Click on the item from that has lowest price in the search list")
    public void click_item_having_lowest_prise() {
        searchPageObjects.lowestPriceItem();
        scn.log("Selecting product having lowest price");
     }
    
    @Then("Product Description is displayed in new tab")
    public void product_description_is_displayed_in_new_tab() {
        WebDriverFactory.switchBrowserToTab();
        scn.log("Switched to the new window/tab");

        productDescriptionPageObjects.ValidateProductTileIsCorrectlyDisplayed();
        productDescriptionPageObjects.ValidateAddToCartButtonIsCorrectlyDisplayed();
    }
    
    @When("Change quantity to {string} then add to cart")
    public void change_Quantity(String quantity) {
    	productDescriptionPageObjects.selectQuantity(quantity);
    	productDescriptionPageObjects.clickOnAddToCartButton();
    	scn.log("Selecting product quantity as : " + quantity);
     }

    @When("Empty Cart")
    public void emptyCart() {
    	productDescriptionPageObjects.EmptyCart();
    	scn.log("Remove the product from the cart");
     }
    
    @When("Validate {string} message")
    public void cartMessage(String message) {
    	productDescriptionPageObjects.CartMessage(message);
    	scn.log("Validated correct message is displayed");
     }
}
