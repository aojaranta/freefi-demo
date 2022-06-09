package fi.free.apilayer;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashMap;

public class ExchangeRateResponse {

	/*
	 * Example data from ApiLayer
	 {
	 "base": "USD",
	 "date": "2021-03-17",
	 "rates": {
	  "EUR": 0.813399,
	  "GBP": 0.72007,
	  "JPY": 107.346001
	 },
	 "success": true,
	 "timestamp": 1519296206
	}
	*/
	
	private String base;
	
	private String date;
	
	private boolean success;
	
	private ZonedDateTime timestamp;
	
	private HashMap<String, BigDecimal> rates;

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public HashMap<String, BigDecimal> getRates() {
		return rates;
	}

	public void setRates(HashMap<String, BigDecimal> rates) {
		this.rates = rates;
	}
	
}
