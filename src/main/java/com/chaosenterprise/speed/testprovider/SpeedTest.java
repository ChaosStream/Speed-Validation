package com.chaosenterprise.speed.testprovider;

import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chaosenterprise.speed.Configurations;
import com.chaosenterprise.speed.data.SpeedTestData;
import com.chaosenterprise.speed.data.TestData;

public class SpeedTest extends WebPageProvider {

	private static final Logger log = LoggerFactory.getLogger(SpeedTest.class);

	private static final String URL = "http://beta.speedtest.net/";

	private static final By RESULTTAG = By.xpath("//div[contains(@class,'result-data')]/span[contains(@class,'upload-speed')]");

	private static final By STARTTAG = By.xpath("//div[@class='start-button']/a");

	public SpeedTest(Configurations configurations) {
		super(configurations);
	}

	@Override
	public TestData getTestResult() {
		return PageFactory.initElements(getWebDriver(), SpeedTestData.class);
	}

	@Override
	protected String getUrl() {
		return URL;
	}

	@Override
	protected By getStartTag() {
		return STARTTAG;
	}

	@Override
	protected By getResultsTag() {
		return RESULTTAG;
	}

	@Override
	protected void waitForResults() {
		log.debug("Waiting for result tag {}", getResultsTag());
		new WebDriverWait(getWebDriver(), 120).until(ExpectedConditions.textMatches(getResultsTag(), Pattern.compile("\\w+")));
		log.debug("Found result tag {}", getResultsTag());
	}

}
