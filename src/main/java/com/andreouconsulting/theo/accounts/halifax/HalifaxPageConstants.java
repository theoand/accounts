package com.andreouconsulting.theo.accounts.halifax;

import static java.lang.Double.valueOf;
import static org.apache.commons.lang3.StringUtils.replacePattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Constants relating to the Halifax page.
 * 
 * @author theo@andreouconsulting.com
 *
 */
public class HalifaxPageConstants {

	static final String HOMEPAGE = "https://www.halifax-online.co.uk/personal/logon/login.jsp";

	// Homepage items
	static final String HOMEPAGE_USERNAME_ID = "frmLogin:strCustomerLogin_userID";

	static final String HOMEPAGE_PASSWORD_ID = "frmLogin:strCustomerLogin_pwd";

	static final String HOMEPAGE_CONTINUE_ID = "frmLogin:btnLogin2";

	// Memorable information items page
	// The dropdowns use this as 'id' and the labels as 'for'
	static final String MEM_INFO_URL = "entermemorableinformation";

	static final String MEM_INFO_1 = "frmentermemorableinformation1:strEnterMemorableInformation_memInfo1";

	static final String MEM_INFO_2 = "frmentermemorableinformation1:strEnterMemorableInformation_memInfo2";

	static final String MEM_INFO_3 = "frmentermemorableinformation1:strEnterMemorableInformation_memInfo3";

	static final String MEM_INFO_CONTINUE_ID = "frmentermemorableinformation1:btnContinue";

	// Accounts Overview page
	static final String ACCOUNTS_BALANCES_NAME_XPATH = "//a[contains(@id,'lnkAccName')]";

	static final String ACCOUNTS_BALANCES_XPATH = "//p[contains(@class,'balance ManageMyAccountsAnchor2')]/span";

	static final String ACCOUNTS_MORTGAGE_BALANCES_XPATH = "//p[contains(@class,'balance')]/span";

	/**
	 * Gets the numeric of the label e.g. For "Character 6 " it will return 6.
	 * 
	 * @param value
	 * @return
	 */
	public static int getNumeralFromLabel(String value) {
		return Integer.valueOf(value.substring(10, 11)).intValue();
	}

	/**
	 * Removes the occurrences of £ and , from the balances.
	 * <p>
	 * e.g. £ 32,000.10 will be translated to 32000.10
	 * 
	 * @param value
	 * @return
	 */
	public static double getAmountFromMoneyLabel(String value) {
		return valueOf(replacePattern(value, "£ |,", ""));
	}
}
