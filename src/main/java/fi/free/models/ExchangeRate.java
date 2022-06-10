package fi.free.models;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "EXCHANGERATES",
indexes = {},
uniqueConstraints = {
		@UniqueConstraint(name="Unq_exchangeRate_base_exchangeRate", columnNames={"base", "exchangeRate"})
})
public class ExchangeRate {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "base")
	private String base;
	
	@Column(name = "date")
	private String date;
		
	@Column(name = "timestamp")
	private Date timestamp;
	
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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
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
