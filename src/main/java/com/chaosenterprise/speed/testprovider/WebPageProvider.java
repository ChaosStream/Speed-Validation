package com.chaosenterprise.speed.testprovider;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chaosenterprise.speed.Configurations;

public abstract class WebPageProvider implements Provider {

	private static final Logger log = LoggerFactory.getLogger(WebPageProvider.class);

	private WebDriver webDriver;

	private Configurations configurations;

	public WebPageProvider(Configurations configurations) {
		this.configurations = configurations;
	}

	public WebDriver getWebDriver() {
		if (webDriver == null) {
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			System.setProperty("webdriver.firefox.bin", "src/main/resources/geckodriver.exe");
			try {
				webDriver = new ChromeDriver();

				if (!configurations.getDebug()) {
					webDriver.manage()
								.window()
								.setSize(new Dimension(10, 10));
				}
			} catch (Exception e) {
				log.warn(e.getLocalizedMessage());
				e.printStackTrace();
			}

		}

		return webDriver;
	}

	@Override
	public void runTest() {
		log.warn("Starting {}", this.getClass()
									.getName());

		getWebDriver().get(getUrl());

		startTest();
		waitForResults();
	}

	@Override
	public boolean testConnection() {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) new URL(getUrl()).openConnection();
			connection.connect();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return true;
	}

	@Override
	public void close() {
		webDriver.quit();
	}

	protected void waitForResults() {
		log.warn("Waiting for result tag {}", getResultsTag());
		new WebDriverWait(getWebDriver(), 60).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getResultsTag()));
		log.warn("Found result tag {}", getResultsTag());
	}

	protected void startTest() {
		log.warn("Clicking start tag {}", getStartTag());
		WebElement button = new WebDriverWait(getWebDriver(), 60).until(ExpectedConditions.elementToBeClickable(getStartTag()));
		button.click();
		log.warn("Start tag clicked");
	}

	abstract protected String getUrl();

	abstract protected By getStartTag();

	abstract protected By getResultsTag();

}
