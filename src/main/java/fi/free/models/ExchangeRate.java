package fi.free.models;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EXCHANGERATES",
indexes = {})
public class ExchangeRate {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "base", unique = true)
	private String base;
	
	@Column(name = "date")
	private String date;
		
	@Column(name = "timestamp")
	private ZonedDateTime timestamp;
	
	@Column(name = "exchangeRate")
	private String exchangeRate;
	
	@Column(name = "exchangeValue")
	private BigDecimal exchangeValue;


	public Long getId() {
		return id;
	}
	
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

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(ZonedDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public BigDecimal getExchangeValue() {
		return exchangeValue;
	}

	public void setExchangeValue(BigDecimal exchangeValue) {
		this.exchangeValue = exchangeValue;
	}
		
}
