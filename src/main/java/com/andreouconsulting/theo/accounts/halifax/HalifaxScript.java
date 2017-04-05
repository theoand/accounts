package com.andreouconsulting.theo.accounts.halifax;

import static com.andreouconsulting.theo.accounts.halifax.HalifaxPageConstants.ACCOUNTS_BALANCES_NAME_XPATH;
import static com.andreouconsulting.theo.accounts.halifax.HalifaxPageConstants.ACCOUNTS_BALANCES_XPATH;
import static com.andreouconsulting.theo.accounts.halifax.HalifaxPageConstants.HOMEPAGE;
import static com.andreouconsulting.theo.accounts.halifax.HalifaxPageConstants.HOMEPAGE_CONTINUE_ID;
import static com.andreouconsulting.theo.accounts.halifax.HalifaxPageConstants.HOMEPAGE_PASSWORD_ID;
import static com.andreouconsulting.theo.accounts.halifax.HalifaxPageConstants.HOMEPAGE_USERNAME_ID;
import static com.andreouconsulting.theo.accounts.halifax.HalifaxPageConstants.MEM_INFO_1;
import static com.andreouconsulting.theo.accounts.halifax.HalifaxPageConstants.MEM_INFO_2;
import static com.andreouconsulting.theo.accounts.halifax.HalifaxPageConstants.MEM_INFO_3;
import static com.andreouconsulting.theo.accounts.halifax.HalifaxPageConstants.MEM_INFO_CONTINUE_ID;
import static com.andreouconsulting.theo.accounts.halifax.HalifaxPageConstants.getAmountFromMoneyLabel;
import static com.andreouconsulting.theo.accounts.halifax.HalifaxPageConstants.getNumeralFromLabel;

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
public class HalifaxScript {

	private SeleniumService service;

	public HalifaxScript(SeleniumService service) {
		this.service = service;
	}

	public Map<String, Double> execute(String username, String password, String secretAnswer) throws Exception {
		Map<String, Double> balances = Maps.newHashMap();

		// Go to homepage
		service.navigateTo(HOMEPAGE);

		// Populate the first log-in page
		service.enterTextToFieldWithId(HOMEPAGE_USERNAME_ID, username);
		service.enterTextToFieldWithId(HOMEPAGE_PASSWORD_ID, password);
		service.clickButtonWithId(HOMEPAGE_CONTINUE_ID);

		// Wait until next page loads
		service.waitForPageToLoad("id", MEM_INFO_1);

		// Populate the secret answer
		int memInfo1Label1 = getMemInfo1LabelNumber(MEM_INFO_1);
		int memInfo1Label2 = getMemInfo1LabelNumber(MEM_INFO_2);
		int memInfo1Label3 = getMemInfo1LabelNumber(MEM_INFO_3);

		service.selectByTextFromInputWithId(MEM_INFO_1, String.valueOf(" " + secretAnswer.toCharArray()[memInfo1Label1 - 1]));
		service.selectByTextFromInputWithId(MEM_INFO_2, String.valueOf(" " + secretAnswer.toCharArray()[memInfo1Label2 - 1]));
		service.selectByTextFromInputWithId(MEM_INFO_3, String.valueOf(" " + secretAnswer.toCharArray()[memInfo1Label3 - 1]));
		service.clickButtonWithId(MEM_INFO_CONTINUE_ID);

		// Wait until next page loads
		service.waitForPageToLoadWithXpath(ACCOUNTS_BALANCES_NAME_XPATH);

		// Get balances
		List<WebElement> accountBalancesNames = service.findElementsWithXpath(ACCOUNTS_BALANCES_NAME_XPATH);
		List<WebElement> accountBalances = service.findElementsWithXpath(ACCOUNTS_BALANCES_XPATH);
		for (int i = 0; i < accountBalances.size(); i++) {
			balances.put("Z - " + accountBalancesNames.get(i).getText(), getAmountFromMoneyLabel(accountBalances.get(i).getText()));
		}

		return balances;
	}

	private int getMemInfo1LabelNumber(String label) {
		return getNumeralFromLabel(service.findElementByAtrribute("for", label).getText());
	}

}
