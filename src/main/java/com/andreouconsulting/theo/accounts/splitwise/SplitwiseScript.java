package com.andreouconsulting.theo.accounts.splitwise;

import static com.andreouconsulting.theo.accounts.splitwise.SplitwisePageConstants.BALANCE_XPATH;
import static com.andreouconsulting.theo.accounts.splitwise.SplitwisePageConstants.LOGIN_PAGE_EMAIL_XPATH;
import static com.andreouconsulting.theo.accounts.splitwise.SplitwisePageConstants.LOGIN_PAGE_PASSWORD_XPATH;
import static com.andreouconsulting.theo.accounts.splitwise.SplitwisePageConstants.getAmountFromMoney;

import java.util.Map;

import org.openqa.selenium.WebElement;

import com.andreouconsulting.theo.selenium.SeleniumService;
import com.google.common.collect.Maps;

/**
 * Script for retrieving balances from Splitwise page.
 * 
 * @author theo@andreouconsulting.com
 *
 */
public class SplitwiseScript {

	private SeleniumService service;

	public SplitwiseScript(SeleniumService service) {
		this.service = service;
	}

	public Map<String, Double> execute(String username, String password) throws Exception {
		Map<String, Double> balances = Maps.newHashMap();

		// Go to homepage
		service.navigateTo(SplitwisePageConstants.LOGIN_PAGE);

		// Populate the log-in page
		service.enterTextToFieldWithXpath(LOGIN_PAGE_EMAIL_XPATH, username);
		service.enterTextToFieldWithXpath(LOGIN_PAGE_PASSWORD_XPATH, password);
		service.clickButtonWithXpath(SplitwisePageConstants.LOGIN_PAGE_CONFIRM_BUTTON_XPATH);

		// Wait until next page loads
		service.waitForPageToLoadWithXpath(BALANCE_XPATH);

		// Get balances
		WebElement accountBalance = service.findElementWithXpath(BALANCE_XPATH);
		balances.put("Splitwise", getAmountFromMoney(accountBalance.getText()));

		return balances;
	}
}