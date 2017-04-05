package com.andreouconsulting.theo.accounts.hsbc;


import static com.andreouconsulting.theo.accounts.hsbc.HsbcPageConstants.HOMEPAGE;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;

import com.andreouconsulting.theo.selenium.SeleniumService;
import com.google.common.collect.Maps;

/**
 * Script for retrieving balances from Halifax page.
 * 
 * @author theo@andreouconsulting.com
 *
 */
public class HsbcScript {

	private SeleniumService service;

	public HsbcScript(SeleniumService service) {
		this.service = service;
	}

	/**
	 * @param username
	 * @param password
	 * @param secretAnswer
	 * @return
	 * @throws Exception
	 */
	public Map<String, Double> execute(String username, String password, String secretAnswer) throws Exception {
		Map<String, Double> balances = Maps.newHashMap();

		// Go to homepage
		service.navigateTo(HOMEPAGE);

		//Click log-in button
		service.waitForPageToLoad("class", HsbcPageConstants.LOGIN_BUTTON_CLASS);
		service.clickButtonWithAttribute("class", HsbcPageConstants.LOGIN_BUTTON_CLASS);
		
		// Populate the username page
		service.waitForPageToLoad("id", HsbcPageConstants.USERNAME_ID);
		service.enterTextToFieldWithId(HsbcPageConstants.USERNAME_ID, username);
		service.clickButtonWithAttribute("class", HsbcPageConstants.USERNAME_CONTINUE_ID);

		// Populate the password page
		service.waitForPageToLoad("type", "submit");
		//select log-on method
		service.clickButtonWithAttribute("href", "withOutSecKey"); 

		//Wait for the memorable answer to load
		service.waitForPageToLoad("id", "pass");
		service.waitForPageToLoad("id", "memorableAnswer");
		service.enterTextToFieldWithId("memorableAnswer", secretAnswer);
		
		List<WebElement> elements = service.findElementsWithXpath("//h4[@class='FontRed']/span");
		
		int memInfo1Label1 = getPasswordNumber(elements.get(0).getText(), password);
		int memInfo1Label2 = getPasswordNumber(elements.get(1).getText(), password);
		int memInfo1Label3 = getPasswordNumber(elements.get(2).getText(), password);
		
		service.enterTextToFieldWithId("pass" + memInfo1Label1, String.valueOf(password.toCharArray()[memInfo1Label1 - 1]));
		service.enterTextToFieldWithId("pass" + memInfo1Label2, String.valueOf(password.toCharArray()[memInfo1Label2 - 1]));
		service.enterTextToFieldWithId("pass" + memInfo1Label3, String.valueOf(password.toCharArray()[memInfo1Label3 - 1]));
		service.clickButtonWithAttribute("class", HsbcPageConstants.USERNAME_CONTINUE_ID);

		// Wait until next page loads
		service.waitForPageToLoadWithXpath("//a/span/span[@class='itemTitle']");
		// Get balances
		List<WebElement> accountBalances = service.findElementsWithXpath("//a/span[@class='fr']/span[@class='itemValue privacy valueTiny']");
		List<WebElement> accountBalancesNames = service.findElementsWithXpath("//a/span/span[@class='itemTitle']");
		for (int i = 0; i < accountBalances.size(); i++) {
			balances.put("HSBC - " + accountBalancesNames.get(i).getText(), HsbcPageConstants.getAmountFromMoneyLabel(accountBalances.get(i).getText()));
		}

		return balances;
	}

	private int getPasswordNumber(String element, String password) {
		if(element.equals("last")){
			return password.length();
		}
		if(element.equals("second to last")){
			return password.length()-1;
		}
		return Integer.valueOf(element.substring(0, 1)).intValue();
	}

	public static int getNumeralFromLabel(String value) {
		return Integer.valueOf(value.substring(10, 11)).intValue();
	}
}