package fi.free.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SSNValidationResponse {

	private boolean isSSNValid;
	
	public SSNValidationResponse(boolean isSSNValid) {
		this.isSSNValid = isSSNValid;
	}

	@JsonProperty(value = "ssn_valid")
	public boolean isSSNValid() {
		return isSSNValid;
	}
	
	public void setSSNValid(boolean isSSNValid) {
		this.isSSNValid = isSSNValid;
	}
	
}
