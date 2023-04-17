@ui @search
Feature: User is able to use search feature
	
	@SmokeTest
    Scenario: User is able to filter the result based on Prices
      Given User navigated to the home application url
      And User Search for product "Gel Pen"
      And Validate all search suggestions contain "Gel Pen" and choose last gel pen option from the suggestions
      And Check the search result ensuring every product item has the "Pen" in its title
      And Click on the item from that has lowest price in the search list
      And Product Description is displayed in new tab
      And Change quantity to "2" then add to cart
      And Empty Cart
      And Validate "... was removed from Shopping Cart." message
     
