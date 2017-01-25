package com.chaosenterprise.speed.data;

import java.util.Date;

import com.chaosenterprise.speed.testprovider.Providers;

public interface TestData {

	public String getUpload();

	public String getUploadUnits();

	public String getDownload();

	public String getDownloadUnits();

	public String getLatency();

	public Date getDate();

	public Providers getSource();

}
