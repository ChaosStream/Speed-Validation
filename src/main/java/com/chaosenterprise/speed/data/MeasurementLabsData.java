package com.chaosenterprise.speed.data;

import java.util.Date;

import lombok.ToString;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@ToString
public class MeasurementLabsData implements TestData {

	private Date date = new Date();

	private String source = "MeasurementLabs";

	@FindBy(id = "upload-speed")
	private WebElement upload;

	@FindBy(id = "upload-speed-units")
	private WebElement uploadUnits;

	@FindBy(id = "download-speed")
	private WebElement download;

	@FindBy(id = "download-speed-units")
	private WebElement downloadUnits;

	@FindBy(id = "latency")
	private WebElement latency;

	public MeasurementLabsData() {
	}

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
	public String getSource() {
		return source;
	}

}
