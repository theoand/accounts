package com.andreouconsulting.theo.accounts;

import static com.andreouconsulting.theo.selenium.FileService.writeToFile;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.andreouconsulting.theo.accounts.amex.AmexScript;
import com.andreouconsulting.theo.accounts.halifax.HalifaxScript;
import com.andreouconsulting.theo.accounts.hsbc.HsbcScript;
import com.andreouconsulting.theo.accounts.santander.SantanderScript;
import com.andreouconsulting.theo.accounts.splitwise.SplitwiseScript;
import com.andreouconsulting.theo.selenium.Browser;
import com.andreouconsulting.theo.selenium.SeleniumService;
import com.beust.jcommander.internal.Maps;

/**
 * Log-in on websites and retrieve the balances of the accounts. 
 * 
 * @author theo@andreouconsulting.com
 *
 */
public class TestSeleniumService {

	// Halifax log-in details
	private static final String HALIFAX_PASSWORD = "XXX";
	private static final String HALIFAX_USERNAME = "XXX";
	private static final String HALIFAX_SECRET_ANSWER = "XXX";
	private static final String HALIFAX_BALANCES_CSV = "Halifax_balances.csv";

	// Splitwise log-in details
	private static final String SPLITWISE_USERNAME = "XXX";
	private static final String SPLITWISE_PASSWORD = "XXX";
	private static final String SPLITWISE_BALANCES_CSV = "Splitwise_balances.csv";

	// AMEX log-in details
	private static final String AMEX_USERNAME = "XXX";
	private static final String AMEX_PASSWORD = "XXX";
	private static final String AMEX_BALANCES_CSV = "AMEX_balances.csv";

	// Santander log-in details
	private static final String SANTANDER_CUSTOMER_ID = "XXX";
	private static final String SANTANDER_PASSCODE = "XXX";
	private static final String SANTANDER_PIN = "XXX";
	private static final String SANTANDER_SECRET_ANSWER_ΒΙRΤΗ = "XXX";
	private static final String SANTANDER_SECRET_ANSWER_SCHOOL = "XXX";
	private static final String SANTANDER_SECRET_ANSWER_MOTHER = "XXX";
	private static final String SANTANDER_BALANCES_CSV = "Santander_balances.csv";

	//HSBC log-in details
	private static final String HSBC_CUSTOMER_ID = "XXX";
	private static final String HSBC_PASSCODE = "XXX";
	private static final String HSBC_SECRET_ANSWER_WORK = "XXX";
	private static final String HSBC_BALANCES_CSV = "HSBC_balances.csv";

	//Other
	private static final String BALANCES_CSV = "Balances.csv";
	private SeleniumService service;

	
	/**
	 * Make sure you shut down the browser.
	 */
	@After
	public void tearDown() {
		service.terminate();
	}

	
	/**
	 * Set-up the browser.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		service = new SeleniumService(Browser.PHANTOMJS, true);
	}

	
	/**
	 * Gets the balances for all 4 websites and addes them on a CSV file.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetBalances() throws Exception {
		Map<String, Double> balances = Maps.newHashMap();

		try{
			FileUtils.deleteDirectory(new File("./selenium_screenshots"));
		} catch (Exception e){
			//continue
		}
		// Get Santander balances
		SantanderScript santanderScript = new SantanderScript(service);
		Map<String, Double> santanderBalances = santanderScript.execute(SANTANDER_CUSTOMER_ID, SANTANDER_PASSCODE, SANTANDER_PIN, SANTANDER_SECRET_ANSWER_ΒΙRΤΗ, SANTANDER_SECRET_ANSWER_SCHOOL,
				SANTANDER_SECRET_ANSWER_MOTHER);

		// Get Halifax balances
		HalifaxScript halifaxScript = new HalifaxScript(service);
		Map<String, Double> halifaxBalances = halifaxScript.execute(HALIFAX_USERNAME, HALIFAX_PASSWORD, HALIFAX_SECRET_ANSWER);

		// Get AMEX balances
		AmexScript amexScript = new AmexScript(service);
		Map<String, Double> amexBalances = amexScript.execute(AMEX_USERNAME, AMEX_PASSWORD);

		// Get Splitwise balances
		SplitwiseScript splitwiseScript = new SplitwiseScript(service);
		Map<String, Double> splitwiseBalances = splitwiseScript.execute(SPLITWISE_USERNAME, SPLITWISE_PASSWORD);

		HsbcScript hsbcScript = new HsbcScript(service);
		Map<String, Double> hsbcBalances = hsbcScript.execute(HSBC_CUSTOMER_ID, HSBC_PASSCODE, HSBC_SECRET_ANSWER_WORK);

		// Prepare the full balances
		balances.putAll(santanderBalances);
		balances.putAll(halifaxBalances);
		balances.putAll(amexBalances);
		balances.putAll(splitwiseBalances);
		balances.putAll(hsbcBalances);
		

		// Write the balances to file
		writeToFile(balances, BALANCES_CSV);
	}

	
	/**
	 * Gets the balances for Hsbc and adds them on a CSV file.
	 * 
	 * @throws Exception
	 */
	public void testGetBalancesForHsbc() throws Exception {
		Map<String, Double> balances = Maps.newHashMap();

		// Get Hsbc balances
		HsbcScript hsbcScript = new HsbcScript(service);

		Map<String, Double> hsbcBalances = hsbcScript.execute(HSBC_CUSTOMER_ID, HSBC_PASSCODE, HSBC_SECRET_ANSWER_WORK);

		// Prepare the full balances
		balances.putAll(hsbcBalances);

		// Write the balances to file
		writeToFile(balances, HSBC_BALANCES_CSV);
	}
	
	
	
	/**
	 * Gets the balances for Halifax and adds them on a CSV file.
	 * 
	 * @throws Exception
	 */
	public void testGetBalancesForHalifax() throws Exception {
		Map<String, Double> balances = Maps.newHashMap();

		// Get Halifax balances
		HalifaxScript halifaxScript = new HalifaxScript(service);
		Map<String, Double> halifaxBalances = halifaxScript.execute(HALIFAX_USERNAME, HALIFAX_PASSWORD, HALIFAX_SECRET_ANSWER);

		// Prepare the full balances
		balances.putAll(halifaxBalances);

		// Write the balances to file
		writeToFile(balances, HALIFAX_BALANCES_CSV);
	}

	
	/**
	 * Gets the balances for Santander and adds them on a CSV file.
	 * 
	 * @throws Exception
	 */
	public void testGetBalancesForSantander() throws Exception {
		Map<String, Double> balances = Maps.newHashMap();

		// Get Santander balances
		SantanderScript santanderScript = new SantanderScript(service);
		Map<String, Double> santanderBalances = santanderScript.execute(SANTANDER_CUSTOMER_ID, SANTANDER_PASSCODE, SANTANDER_PIN, SANTANDER_SECRET_ANSWER_ΒΙRΤΗ, SANTANDER_SECRET_ANSWER_SCHOOL,
				SANTANDER_SECRET_ANSWER_MOTHER);

		// Prepare the full balances
		balances.putAll(santanderBalances);

		// Write the balances to file
		writeToFile(balances, SANTANDER_BALANCES_CSV);
	}

	
	/**
	 * Gets the balances for AMEX and adds them on a CSV file.
	 * 
	 * @throws Exception
	 */
	public void testGetBalancesForAmex() throws Exception {
		Map<String, Double> balances = Maps.newHashMap();

		// Get AMEX balances
		AmexScript amexScript = new AmexScript(service);
		Map<String, Double> amexBalances = amexScript.execute(AMEX_USERNAME, AMEX_PASSWORD);

		// Prepare the full balances
		balances.putAll(amexBalances);

		// Write the balances to file
		writeToFile(balances, AMEX_BALANCES_CSV);
	}

	
	/**
	 * Gets the balances for Splitwise and adds them on a CSV file.
	 * 
	 * @throws Exception
	 */
	public void testGetBalancesForSplitwise() throws Exception {
		Map<String, Double> balances = Maps.newHashMap();

		// Get Splitwise balances
		SplitwiseScript splitwiseScript = new SplitwiseScript(service);
		Map<String, Double> splitwiseBalances = splitwiseScript.execute(SPLITWISE_USERNAME, SPLITWISE_PASSWORD);

		// Prepare the full balances
		balances.putAll(splitwiseBalances);

		// Write the balances to file
		writeToFile(balances, SPLITWISE_BALANCES_CSV);
	}
}
