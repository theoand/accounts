package com.andreouconsulting.theo.accounts.splitwise;

import static com.andreouconsulting.theo.accounts.splitwise.SplitwisePageConstants.BALANCE_XPATH;
import static com.andreouconsulting.theo.accounts.splitwise.SplitwisePageConstants.DASHBOARD_XPATH;
import static com.andreouconsulting.theo.accounts.splitwise.SplitwisePageConstants.LOGIN_PAGE_CONFIRM_BUTTON_XPATH;
import static com.andreouconsulting.theo.accounts.splitwise.SplitwisePageConstants.LOGIN_PAGE_EMAIL_XPATH;
import static com.andreouconsulting.theo.accounts.splitwise.SplitwisePageConstants.LOGIN_PAGE_PASSWORD_XPATH;
import static com.andreouconsulting.theo.accounts.splitwise.SplitwisePageConstants.SPLITWISE;
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
		service.clickButtonWithXpath(LOGIN_PAGE_CONFIRM_BUTTON_XPATH);

		try{
			service.findElementWithXpath(SplitwisePageConstants.PAY_XPATH).click();
		} catch(Exception e){
			//do nothing
		}
		// Wait until next page loads
		service.waitForPageToLoadWithXpath(DASHBOARD_XPATH);

		// Get balances
		try{
			WebElement accountBalance = service.findElementWithXpath(BALANCE_XPATH);
			balances.put(SPLITWISE, getAmountFromMoney(accountBalance.getText()));
		} catch (Exception e){
			balances.put(SPLITWISE, 0.0);
		}

		return balances;
	}
}
