package fi.free.scheduled.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fi.free.apilayer.ApiLayerService;

@Component
public class GetLatestExchangeRateJob implements Job {

	private final Logger log = LoggerFactory.getLogger(GetLatestExchangeRateJob.class);
	 
	private final ApiLayerService apiLayerService;
	
	private final String[] currencies = new String[] {"EUR", "SEK", "USD"};
	
	public GetLatestExchangeRateJob(ApiLayerService apiLayerService) {
		this.apiLayerService = apiLayerService;
	}
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("Executing Quartz job GetLatestExchangeRateJob {}", context.getJobDetail().getKey());
		for(String baseCurrency : currencies)
			apiLayerService.getLatestExchangeRatesFromApiLayer(baseCurrency, currencies);
	}

}
