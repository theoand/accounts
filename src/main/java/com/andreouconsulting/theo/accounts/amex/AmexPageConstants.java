package com.andreouconsulting.theo.accounts.amex;

import static org.apache.commons.lang3.StringUtils.replace;

public class AmexPageConstants {

	public static final String LOGIN_PAGE = "https://global.americanexpress.com/myca/logon/emea/action?request_type=LogonHandler&Face=en_GB";

	public static final String LOGIN_PAGE_EMAIL_ID = "UserID";

	public static final String LOGIN_PAGE_PASSWORD_ID = "Password";

	public static final String LOGIN_PAGE_COOKIES_BUTTON_ID = "sprite-ContinueButton_EN";

	public static final String LOGIN_PAGE_CONFIRM_BUTTON_ID = "lilo_formSubmit";

	public static final String BALANCE_XPATH = "balance-data";

	public static Double getAmountFromMoney(String amount) {
		return Double.valueOf(replace(replace(amount, "£", ""), " ", ""));
	}
}
