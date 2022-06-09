package fi.free.api.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;

public class SSNValidationRequest {

	@Size(min=11 , max=11, message="Social security number should be 11 charachters long")
	private String ssn;
	
	@JsonProperty(value = "country_code")
	@Pattern(regexp="^FI$", flags= {Flag.CASE_INSENSITIVE}, message="Only FI coutryCode is supported currently")
	private String countryCode;

	public String getSSN() {
		return ssn;
	}

	public void setSSN(String ssn) {
		this.ssn = ssn;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	
}
