package com.chaosenterprise.speed.testprovider;

import java.util.Arrays;
import java.util.List;

public enum Providers {

	SpeedTest("SpeedTest"), MeasurementLabs("MeasurementLabs");

	private String clazz;

	Providers(String className) {
		this.clazz = this.getClass()
							.getPackage()
							.getName() + "." + className;

	}

	public String getClazz() {
		return clazz;
	}

	public static List<Providers> asList() {
		return Arrays.asList(Providers.values());
	}

}
