package fi.free.services;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import fi.free.models.ExchangeRate;
import fi.free.repositories.ExchangeRateRepository;

@Service
public class ExchangeRateService {

	private final Logger log = LoggerFactory.getLogger(ExchangeRateService.class);
	
	private final ExchangeRateRepository exchangeRateRepository;

	public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
		this.exchangeRateRepository = exchangeRateRepository;
	}

	/**
	 * Get locally stored exchange rate values.
	 * 
	 * @param from
	 * @param to
	 * @return ExchangeRate 
	 */
	public ExchangeRate getExchangeRate(String from, String to) {
		return exchangeRateRepository.findExchangeRate(from, to).orElseThrow();
	}
	
	/**
	 * Calculate exchange amount based on given exchange rate
	 * 
	 * @param amount
	 * @param exchangeRate
	 * @return BigDecimal Calculated exchange amount
	 */
	public BigDecimal calculateExchangeAmount(BigDecimal amount, BigDecimal exchangeRate) {
		BigDecimal result = amount.multiply(exchangeRate);
		log.info("Exchange calculation result {} {}", result, exchangeRate);
		return result;
	}
	
	

}
