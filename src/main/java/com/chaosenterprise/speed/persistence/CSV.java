package com.chaosenterprise.speed.persistence;

import java.io.IOException;

import com.chaosenterprise.speed.data.TestData;

public class CSV extends FilePersistence {

	private String fileName = "speed-validation.csv";

	public CSV() {
	}

	private String format(TestData testData) {
		return String.format("%s,%s,%s,%s,%s,%s,%s\n", testData.getUpload(), testData.getUploadUnits(), testData.getDownload(), testData.getDownloadUnits(), testData.getLatency(), testData.getDate(), testData.getSource());
	}

	@Override
	public void save(TestData testData) {
		try {
			writeToFile(format(testData), true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	@Override
	String getFileName() {
		return fileName;
	}
}
