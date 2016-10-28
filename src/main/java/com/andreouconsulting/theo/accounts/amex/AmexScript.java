package com.andreouconsulting.theo.accounts.amex;

import static com.andreouconsulting.theo.accounts.amex.AmexPageConstants.BALANCE_XPATH;
import static com.andreouconsulting.theo.accounts.amex.AmexPageConstants.LOGIN_PAGE;
import static com.andreouconsulting.theo.accounts.amex.AmexPageConstants.LOGIN_PAGE_CONFIRM_BUTTON_ID;
import static com.andreouconsulting.theo.accounts.amex.AmexPageConstants.LOGIN_PAGE_COOKIES_BUTTON_ID;
import static com.andreouconsulting.theo.accounts.amex.AmexPageConstants.LOGIN_PAGE_EMAIL_ID;
import static com.andreouconsulting.theo.accounts.amex.AmexPageConstants.LOGIN_PAGE_PASSWORD_ID;
import static com.andreouconsulting.theo.accounts.amex.AmexPageConstants.getAmountFromMoney;

import java.util.Map;

import org.openqa.selenium.WebElement;

import com.andreouconsulting.theo.selenium.SeleniumService;
import com.google.common.collect.Maps;

/**
 * Script for retrieving balances from AMEX page.
 * 
 * @author theo@andreouconsulting.com
 *
 */
public class AmexScript {

	private SeleniumService service;

	public AmexScript(SeleniumService service) {
		this.service = service;
	}

	public Map<String, Double> execute(String username, String password) throws Exception {
		Map<String, Double> balances = Maps.newHashMap();

		// Go to homepage
		service.navigateTo(LOGIN_PAGE);

		// Populate the first log-in page
		service.clickButtonWithId(LOGIN_PAGE_COOKIES_BUTTON_ID);
		service.enterTextToFieldWithId(LOGIN_PAGE_EMAIL_ID, username);
		service.enterTextToFieldWithId(LOGIN_PAGE_PASSWORD_ID, password);
		service.clickButtonWithId(LOGIN_PAGE_CONFIRM_BUTTON_ID);

		// Wait until next page loads
		service.waitForPageToLoadWithXpath(BALANCE_XPATH);

		// Get balances
		WebElement accountBalance = service.findElementWithXpath(BALANCE_XPATH);
		balances.put("AMEX", getAmountFromMoney(accountBalance.getText()));

		return balances;
	}
}
