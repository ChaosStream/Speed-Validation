package com.chaosenterprise.speed.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chaosenterprise.speed.Configurations;

public class PersistenceFactory {

	private static final Logger log = LoggerFactory.getLogger(PersistenceFactory.class);

	public static Persistence loadPersistence(Persistences persistence) {
		try {
			return (Persistence) PersistenceFactory.class.getClassLoader()
															.loadClass(persistence.getClazz())
															.newInstance();

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			log.error("Failed to find class {}", persistence.getClazz());
			log.error(e.getLocalizedMessage());
			return null;
		}
	}

	public static Persistence loadPersistence(String name) {
		try {

			return (Persistence) PersistenceFactory.class.getClassLoader()
															.loadClass(PersistenceFactory.class.getPackage()
																								.getName() + "." + name)
															.newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			log.error("Failed to find class {}", name);
			log.error(e.getLocalizedMessage());
			return null;
		}
	}

	public static List<Persistence> getConfiguredPersistences(Configurations configurations) {

		return configurations.getPersistences()
								.stream()
								.map(PersistenceFactory::loadPersistence)
								.collect(Collectors.toList());

	}
}
