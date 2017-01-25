package com.chaosenterprise.speed.persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chaosenterprise.speed.data.TestData;
import com.chaosenterprise.speed.testprovider.Providers;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json extends FilePersistence {

	private static final Logger log = LoggerFactory.getLogger(Json.class);

	private static final String fileName = "speed-validation.json";

	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public void save(TestData testData) {
		// mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		try {
			JsonData jsonData = loadJsonData(getFileName());

			if (jsonData != null && jsonData.data == null) {
				jsonData.data = new ArrayList<Data>();
			}

			jsonData.data.add(new Data(testData));

			mapper.writeValue(getFile(getFileName()), jsonData);
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	protected JsonData loadJsonData(String fileName) {
		try {
			return mapper.readValue(getFile(fileName), JsonData.class);
		} catch (IOException e) {
			log.warn("IOException found {}", e.getLocalizedMessage());
			return new JsonData();
		}
	}

	@Override
	String getFileName() {
		return fileName;
	}

	@JsonInclude(JsonInclude.Include.ALWAYS)
	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	static class JsonData {
		@JsonProperty("data")
		private List<Data> data;

		public JsonData() {
			this(null);
		}

		@JsonCreator
		public JsonData(@JsonProperty("data") List<Data> data) {
			this.data = data;
		}

		public List<Data> getData() {
			return data;
		}
	}

	@JsonInclude(JsonInclude.Include.ALWAYS)
	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	static class Data {
		@JsonProperty("date")
		private Date date;

		@JsonProperty("source")
		private Providers source;

		@JsonProperty("upload")
		private Double upload;

		@JsonProperty("uploadUnits")
		private String uploadUnits;

		@JsonProperty("download")
		private Double download;

		@JsonProperty("downloadUnits")
		private String downloadUnits;

		@JsonProperty("latency")
		private Long latency;

		public Data() {
			this(null);
		}

		@JsonCreator
		public Data(@JsonProperty("date") Date date, @JsonProperty("source") Providers source, @JsonProperty("upload") Double upload, @JsonProperty("uploadUnits") String uploadUnits, @JsonProperty("download") Double download,
				@JsonProperty("downloadUnits") String downloadUnits, @JsonProperty("latency") Long latency) {
			super();
			this.date = date;
			this.source = source;
			this.upload = upload;
			this.uploadUnits = uploadUnits;
			this.download = download;
			this.downloadUnits = downloadUnits;
			this.latency = latency;
		}

		public Data(TestData testData) {
			this.date = testData.getDate();
			this.source = testData.getSource();
			this.upload = Double.valueOf(testData.getUpload());
			this.uploadUnits = testData.getUploadUnits();
			this.download = Double.valueOf(testData.getDownload());
			this.downloadUnits = testData.getDownloadUnits();
			this.latency = Long.valueOf(testData.getLatency());
		}

		public Date getDate() {
			return date;
		}

		public Providers getSource() {
			return source;
		}

		public Double getUpload() {
			return upload;
		}

		public String getUploadUnits() {
			return uploadUnits;
		}

		public Double getDownload() {
			return download;
		}

		public String getDownloadUnits() {
			return downloadUnits;
		}

		public Long getLatency() {
			return latency;
		}

		public void setUpload(Double upload) {
			this.upload = upload;
		}

		public void setUploadUnits(String uploadUnits) {
			this.uploadUnits = uploadUnits;
		}

		public void setDownload(Double download) {
			this.download = download;
		}

		public void setDownloadUnits(String downloadUnits) {
			this.downloadUnits = downloadUnits;
		}

	}

}
