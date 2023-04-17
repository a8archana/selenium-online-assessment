package com.amazon.automation.pageobjects;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductValidation {
	private static final Logger logger = LogManager.getLogger(ProductValidation.class);
	WebDriver driver;

	private By search_text_box = By.id("twotabsearchtextbox");
	private By search_Results = By.xpath("//div[@id='nav-flyout-searchAjax']//div[@class='s-suggestion-container']/div");
	private By search_Results_LastRecord = By.xpath("(//div[@id='nav-flyout-searchAjax']//div[@class='s-suggestion-container']/div)[last()]");
	
	public ProductValidation(WebDriver driver) {
		this.driver = driver;
	}

	public void SetSearchTextBox(String text) {
		WebDriverWait webDriverWait = new WebDriverWait(driver,20);
		WebElement elementSearchBox = webDriverWait.until(ExpectedConditions.elementToBeClickable(search_text_box));
		elementSearchBox.clear();
		elementSearchBox.sendKeys(text);
		logger.info("Value entered in search box: " + text);
	}

	public void ValidateSearchResultAndClickLastOption(String text) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<WebElement> listOfProducts = driver.findElements(search_Results);
		for(WebElement element:listOfProducts) {
			element.getText().contains(text);
			
		}
		driver.findElement(search_Results_LastRecord).click();;
		logger.info("Clicked on last Search Option");
	}

	
	
	
	public void validatePageTitleMatch(String expectedTitle) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		Boolean b = wait.until(ExpectedConditions.titleContains(expectedTitle));
		Assert.assertEquals("Title Validation",true, b);
		logger.info("Page title matched: " + expectedTitle );
	}

}
