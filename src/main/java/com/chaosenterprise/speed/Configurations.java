package com.chaosenterprise.speed;

import java.util.Arrays;
import java.util.List;

import lombok.ToString;

import com.chaosenterprise.speed.persistence.Persistences;
import com.chaosenterprise.speed.testprovider.Providers;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class Configurations {

	@JsonProperty(value = "Providers")
	private List<Providers> providers = Arrays.asList(Providers.MeasurementLabs);

	@JsonProperty(value = "Persistences")
	private List<Persistences> persistences = Persistences.asList();

	@JsonProperty(value = "Cron")
	private String cron = "0 0/30 * 1/1 * ? *";

	@JsonProperty("Debug")
	private Boolean debug = true;

	public Boolean getDebug() {
		return debug;
	}

	public String getCron() {
		return cron;
	}

	public List<Providers> getProviders() {
		return providers;
	}

	public List<Persistences> getPersistences() {
		return persistences;
	}
}
