package com.chaosenterprise.speed.testprovider;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import com.chaosenterprise.speed.Configurations;
import com.chaosenterprise.speed.data.MeasurementLabsData;
import com.chaosenterprise.speed.data.TestData;

public class MeasurementLabs extends WebPageProvider {

	private static final String URL = "http://www.measurementlab.net/p/ndt-ws.html";

	private static final By RESULTTAG = By.id("upload-speed");

	private static final By STARTTAG = By.id("start-button");

	public MeasurementLabs(Configurations configurations) {
		super(configurations);
	}

	@Override
	public TestData getTestResult() {
		return PageFactory.initElements(getWebDriver(), MeasurementLabsData.class);
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

}
