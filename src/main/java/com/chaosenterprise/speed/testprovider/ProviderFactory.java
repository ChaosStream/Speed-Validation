package com.chaosenterprise.speed.testprovider;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chaosenterprise.speed.Configurations;
import com.chaosenterprise.speed.persistence.PersistenceFactory;

public class ProviderFactory {

	private static final Logger log = LoggerFactory.getLogger(ProviderFactory.class);

	public ProviderFactory() {

	}

	public static Provider loadProvider(Providers providers, Configurations configurations) {
		try {
			return (Provider) PersistenceFactory.class.getClassLoader()
														.loadClass(providers.getClazz())
														.getDeclaredConstructor(Configurations.class)
														.newInstance(configurations);

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			log.error("Failed to find class {}", providers.getClazz());
			log.error(e.getLocalizedMessage());
			return null;
		}
	}

	public static Provider loadProvider(String name) {
		try {
			return (Provider) PersistenceFactory.class.getClassLoader()
														.loadClass(ProviderFactory.class.getPackage()
																						.getName() + "." + name)
														.newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			log.error("Failed to find class {}", name);
			log.error(e.getLocalizedMessage());
			return null;
		}
	}

	public static List<Provider> getConfiguredProviders(Configurations configurations) {
		return configurations.getProviders()
								.stream()
								.map(x -> loadProvider(x, configurations))
								.collect(Collectors.toList());
	}

}
