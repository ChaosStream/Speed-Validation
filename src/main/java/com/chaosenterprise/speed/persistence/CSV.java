package com.chaosenterprise.speed.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.chaosenterprise.speed.data.TestData;

public class CSV implements Persistence {

	String fileName = "speed-validation.csv";

	public CSV() {
	}

	private String format(TestData testData) {
		return String.format("%s,%s,%s,%s,%s,%s,%s\n", testData.getUpload(), testData.getUploadUnits(), testData.getDownload(), testData.getDownloadUnits(), testData.getLatency(), testData.getDate(), testData.getSource());
	}

	@Override
	public void save(TestData testData) {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			File file = new File(fileName);

			if (!file.exists()) {
				file.createNewFile();
			}

			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);

			bw.write(format(testData));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
