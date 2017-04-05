package com.andreouconsulting.theo.accounts.santander;

import static java.lang.Double.valueOf;
import static org.apache.commons.lang3.StringUtils.replacePattern;

public class SantanderPageConstants {

	public static final String LOGIN_PAGE = "http://www.santander.co.uk/uk/index";

	public static final String LOGIN_PAGE_BUTTON_CLASS = "buttonLogin2";
	
	public static final String LOGIN_PAGE_CUSTOMER_ID = "infoLDAP_E.customerID";

	public static final String LOGIN_PAGE_SECRET_QUESTION_LABEL_XPATH = "//form/fieldset/div/div/span[@class='data']";

	public static final String LOGIN_PAGE_SECRET_QUESTION_ID = "cbQuestionChallenge.responseUser";

	public static final String LOGIN_PAGE_SECRET_QUESTION_CONTINUE_ID = "buttons.1";
	
	public static final String LOGIN_PAGE_PASSCODE_ID = "authentication.PassCode";

	public static final String LOGIN_PAGE_PIN_ID = "authentication.ERN";

	public static final String LOGIN_PAGE_SPLASH_BUTTON_ID = "splash-97123-close-button";

	public static final String LOGIN_PAGE_CONFIRM_BUTTON_ID = "btnFO";

	public static final String BALANCE_NAMES_XPATH = "//ul[@class='accountlist']/li/div[@class='info']/span/a";

	public static final String BALANCE_AMOUNTS_XPATH = "//ul[@class='accountlist']/li/div[@class='balance']/span[@class='amount']";

	public static Double getAmountFromMoney(String amount) {
		return valueOf(replacePattern(amount, "£|,", ""));
	}
}
