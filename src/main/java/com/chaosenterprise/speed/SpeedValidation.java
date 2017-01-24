package com.chaosenterprise.speed;

import java.io.IOException;
import java.text.ParseException;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class SpeedValidation {

	public static void main(String[] args) throws SchedulerException, ParseException, JsonParseException, JsonMappingException, IOException {

		Configurations configurations = ConfigurationUtil.loadConfigurations();
		ConfigurationUtil.saveConfigurations(configurations);

		JobDetail job = JobBuilder.newJob(ValidateJob.class)
									.withIdentity("ValidateJob")
									.build();

		job.getJobDataMap()
			.put("Configurations", configurations);

		Trigger trigger = TriggerBuilder.newTrigger()
										.withIdentity("ValidateJobTrigger")
										.withSchedule(CronScheduleBuilder.cronSchedule(configurations.getCron()))
										.forJob(job)
										.build();

		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		scheduler.start();
		scheduler.scheduleJob(job, trigger);

	}
}
