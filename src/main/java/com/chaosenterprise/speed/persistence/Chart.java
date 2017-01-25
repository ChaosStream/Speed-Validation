package com.chaosenterprise.speed.persistence;

import java.io.IOException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.chaosenterprise.speed.data.TestData;

/*
 * Requires JSON to be enabled too. 
 */
public class Chart extends Json {

	private static final Logger log = LoggerFactory.getLogger(Chart.class);

	private static final String fileName = "speed-validation.html";

	private TemplateEngine templateEngine;

	public Chart() {
		ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();

		resolver.setTemplateMode(TemplateMode.HTML);
		resolver.setPrefix("");
		resolver.setSuffix(".html");

		templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(resolver);

	}

	@Override
	public void save(TestData testData) {
		try {
			writeToFile(parseTemplate(loadJsonData(super.getFileName())), false);
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	private String parseTemplate(JsonData jsonData) {
		Locale locale = Locale.getDefault();
		final Context context = new Context(locale);

		/*
		 * ToDo: Change this at some point.
		 */

		jsonData.getData()
				.forEach(x -> x.setDownload(scaleValueToMbs(x.getDownload(), x.getDownloadUnits())));
		jsonData.getData()
				.forEach(x -> x.setUpload(scaleValueToMbs(x.getUpload(), x.getUploadUnits())));

		context.setVariable("jsonData", jsonData.getData());
		return templateEngine.process("Speed-Validation", context);
	}

	private Double scaleValueToMbs(Double val, String units) {
		if (units.contains("kb")) {
			return val / 1000.0;
		} else if (units.contains("Mb")) {
			return val;
		}
		return val;
	}

	@Override
	String getFileName() {
		return fileName;
	}
}
