package com.chaosenterprise.speed;

import java.util.List;
import java.util.stream.Collectors;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chaosenterprise.speed.data.TestData;
import com.chaosenterprise.speed.persistence.Persistence;
import com.chaosenterprise.speed.persistence.PersistenceFactory;
import com.chaosenterprise.speed.testprovider.Provider;
import com.chaosenterprise.speed.testprovider.ProviderFactory;

@DisallowConcurrentExecution
public class ValidateJob implements Job {

	private static final Logger log = LoggerFactory.getLogger(ValidateJob.class);

	public ValidateJob() {

	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.trace("Starting tests");

		Configurations configurations = (Configurations) context.getJobDetail()
																.getJobDataMap()
																.get("Configurations");

		if (configurations == null) {
			throw new NullPointerException("Configurations are null. Will not attempt to continue");
		}

		List<Provider> providers = ProviderFactory.getConfiguredProviders(configurations);
		List<Persistence> persistences = PersistenceFactory.getConfiguredPersistences(configurations);

		try {
			log.debug("Removing providers that aren't reachable");
			providers.stream()
						.filter(Provider::testConnection);

			providers.stream()
						.forEach(Provider::runTest);

			log.debug("Getting test results");

			List<TestData> testResults = providers.stream()
													.map(Provider::getTestResult)
													.collect(Collectors.toList());

			log.debug("Persisting test results");
			if (persistences == null) {
				return;
			}
			for (TestData testData : testResults) {
				persistences.stream()
							.forEach(x -> x.save(testData));
			}
		} finally {
			providers.stream()
						.forEach(Provider::close);
		}

		log.trace("Finished tests");

	}

}
