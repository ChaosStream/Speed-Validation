package com.chaosenterprise.speed.persistence;

import java.util.Arrays;
import java.util.List;

public enum Persistences {

	CSV("CSV");

	private String clazz;

	Persistences(String className) {
		this.clazz = this.getClass()
							.getPackage()
							.getName() + "." + className;

	}

	public String getClazz() {
		return clazz;
	}

	public static List<Persistences> asList() {
		return Arrays.asList(Persistences.values());
	}
}
