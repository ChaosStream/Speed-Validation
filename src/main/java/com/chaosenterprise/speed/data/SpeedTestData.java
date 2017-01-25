package com.chaosenterprise.speed.data;

import java.util.Date;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.chaosenterprise.speed.testprovider.Providers;

public class SpeedTestData implements TestData {

	private Date date = new Date();

	private Providers source = Providers.SpeedTest;

	@FindBy(xpath = "//span[contains(@class,'upload-speed')]")
	private WebElement upload;

	@FindBy(xpath = "//span[contains(@class,'upload-speed')]/../span[@class='result-data-unit']")
	private WebElement uploadUnits;

	@FindBy(xpath = "//span[contains(@class,'download-speed')]")
	private WebElement download;

	@FindBy(xpath = "//span[contains(@class,'download-speed')]/../span[@class='result-data-unit']")
	private WebElement downloadUnits;

	@FindBy(xpath = "//span[contains(@id,'ping-value')]")
	private WebElement latency;

	@Override
	public String getUpload() {
		return upload.getText();
	}

	@Override
	public String getUploadUnits() {
		return uploadUnits.getText();
	}

	@Override
	public String getDownload() {
		return download.getText();
	}

	@Override
	public String getDownloadUnits() {
		return downloadUnits.getText();
	}

	@Override
	public String getLatency() {
		return latency.getText();
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public Providers getSource() {
		return source;
	}
}
