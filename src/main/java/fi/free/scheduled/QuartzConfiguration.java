package fi.free.scheduled;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fi.free.scheduled.jobs.GetLatestExchangeRateJob;

@Configuration
public class QuartzConfiguration {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Bean
	public JobDetail jobDetail() {
		return newJob().ofType(GetLatestExchangeRateJob.class).storeDurably().build();
	}

	@Bean
	public Trigger trigger(JobDetail job) {
		int interval = 3600;
		return newTrigger().forJob(job).withSchedule(simpleSchedule().withIntervalInSeconds(interval).repeatForever()).build();
	}
}
