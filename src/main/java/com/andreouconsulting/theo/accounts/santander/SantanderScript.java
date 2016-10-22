package com.andreouconsulting.theo.accounts.santander;

import static com.andreouconsulting.theo.accounts.santander.SantanderPageConstants.BALANCE_AMOUNTS_XPATH;
import static com.andreouconsulting.theo.accounts.santander.SantanderPageConstants.BALANCE_NAMES_XPATH;
import static com.andreouconsulting.theo.accounts.santander.SantanderPageConstants.LOGIN_PAGE_CONFIRM_BUTTON_ID;
import static com.andreouconsulting.theo.accounts.santander.SantanderPageConstants.LOGIN_PAGE_CUSTOMER_ID;
import static com.andreouconsulting.theo.accounts.santander.SantanderPageConstants.LOGIN_PAGE_PASSCODE_ID;
import static com.andreouconsulting.theo.accounts.santander.SantanderPageConstants.LOGIN_PAGE_PIN_ID;
import static com.andreouconsulting.theo.accounts.santander.SantanderPageConstants.LOGIN_PAGE_SECRET_QUESTION_CONTINUE_ID;
import static com.andreouconsulting.theo.accounts.santander.SantanderPageConstants.LOGIN_PAGE_SECRET_QUESTION_ID;
import static com.andreouconsulting.theo.accounts.santander.SantanderPageConstants.LOGIN_PAGE_SECRET_QUESTION_LABEL_XPATH;
import static com.andreouconsulting.theo.accounts.santander.SantanderPageConstants.LOGIN_PAGE_SPLASH_BUTTON_ID;
import static com.andreouconsulting.theo.accounts.santander.SantanderPageConstants.getAmountFromMoney;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;

import com.andreouconsulting.theo.selenium.SeleniumService;
import com.google.common.collect.Maps;

/**
 * Script for retrieving balances from Santander page.
 * 
 * @author theo@andreouconsulting.com
 *
 */
public class SantanderScript {

	private SeleniumService service;

	public SantanderScript(SeleniumService service) {
		this.service = service;
	}

	public Map<String, Double> execute(String customerId, String password, String pin, String placeOfBirth, String firstSchool, String mothers) throws Exception {
		Map<String, Double> balances = Maps.newHashMap();

		// Go to homepage
		service.navigateTo(SantanderPageConstants.LOGIN_PAGE);

		// Remove the advertisment if it exists
		try {
			service.findElementById(LOGIN_PAGE_SPLASH_BUTTON_ID);
			service.clickButtonWithId(LOGIN_PAGE_SPLASH_BUTTON_ID);
		} catch (Exception e) {
			// do nothing. No ad shown
		}

		// Populate the first log-in page
		service.enterTextToFieldWithId(LOGIN_PAGE_CUSTOMER_ID, customerId);
		service.clickButtonWithId(LOGIN_PAGE_CONFIRM_BUTTON_ID);

		// Wait to load
		service.waitForPageToLoad("name", LOGIN_PAGE_SECRET_QUESTION_CONTINUE_ID);

		// Populate the secret answer
		try {
			String question = service.findElementWithXpath(LOGIN_PAGE_SECRET_QUESTION_LABEL_XPATH).getText();
			if (question.contains("school")) {
				service.enterTextToFieldWithId(LOGIN_PAGE_SECRET_QUESTION_ID, firstSchool);
			} else if (question.contains("Birth")) {
				service.enterTextToFieldWithId(LOGIN_PAGE_SECRET_QUESTION_ID, placeOfBirth);
			} else if (question.contains("Mother")) {
				service.enterTextToFieldWithId(LOGIN_PAGE_SECRET_QUESTION_ID, mothers);
			}
			service.clickButtonWithAttribute("name", LOGIN_PAGE_SECRET_QUESTION_CONTINUE_ID);
		} catch (Exception e) {
			// No need for secret answer
		}

		// Populate the last password
		service.enterTextToFieldWithId(LOGIN_PAGE_PASSCODE_ID, password);
		service.enterTextToFieldWithId(LOGIN_PAGE_PIN_ID, pin);
		service.clickButtonWithAttribute("name", LOGIN_PAGE_SECRET_QUESTION_CONTINUE_ID);

		// Wait until next page loads
		service.waitForPageToLoadWithXpath(BALANCE_NAMES_XPATH);

		// Get balances
		List<WebElement> accountBalancesNames = service.findElementsWithXpath(BALANCE_NAMES_XPATH);
		List<WebElement> accountBalances = service.findElementsWithXpath(BALANCE_AMOUNTS_XPATH);
		for (int i = 0; i < accountBalances.size(); i++) {
			balances.put(accountBalancesNames.get(i).getText(), getAmountFromMoney(accountBalances.get(i).getText()));
		}

		return balances;
	}
}
