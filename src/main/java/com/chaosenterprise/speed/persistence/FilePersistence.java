package com.chaosenterprise.speed.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class FilePersistence implements Persistence {

	public FilePersistence() {
	}

	abstract String getFileName();

	protected File getFile(String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	protected void writeToFile(String content, Boolean append) throws IOException {
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			File file = getFile(getFileName());

			fw = new FileWriter(file.getAbsoluteFile(), append);
			bw = new BufferedWriter(fw);

			bw.write(content);
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
