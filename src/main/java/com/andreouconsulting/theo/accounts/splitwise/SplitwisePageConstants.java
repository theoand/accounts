package com.andreouconsulting.theo.accounts.splitwise;

import static java.lang.Double.valueOf;

import org.apache.commons.lang3.StringUtils;

public class SplitwisePageConstants {

	public static final String LOGIN_PAGE = "https://secure.splitwise.com/login";

	public static final String LOGIN_PAGE_EMAIL_XPATH = "//div[@class='clearfix']/div[@class='input']//input[@id='user_session_email']";

	public static final String LOGIN_PAGE_PASSWORD_XPATH = "//div[@class='clearfix']/div[@class='input']//input[@id='user_session_password']";

	public static final String LOGIN_PAGE_CONFIRM_BUTTON_XPATH = "//input[@value='Log in']";

	public static final String BALANCE_XPATH = "//div[@id='dashboard_balances']/div[@class='total_balances']/div[@class='block'][1]/span";

	public static Double getAmountFromMoney(String amount) {
		return valueOf(StringUtils.replacePattern(amount, "£| ", ""));
	}
}
