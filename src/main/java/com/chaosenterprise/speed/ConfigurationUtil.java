package com.chaosenterprise.speed;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ConfigurationUtil {

	private static final String propertyFile = "speed-validation-properties.json";

	private static ObjectMapper mapper = new ObjectMapper();

	public static Configurations loadConfigurations() throws JsonParseException, JsonMappingException, IOException {
		return mapper.readValue(getFile(), Configurations.class);
	}

	public static void saveConfigurations(Configurations configurations) throws JsonGenerationException, JsonMappingException, IOException {
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.writeValue(getFile(), configurations);
	}

	private static File getFile() throws IOException {
		File file = new File(propertyFile);
		if (!file.exists()) {
			file.createNewFile();
			saveConfigurations(new Configurations());
		}
		return file;
	}

}
