package fi.free.api.controllers;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fi.free.api.dto.ExchangeCurrencyResponse;
import fi.free.api.dto.SSNValidationRequest;
import fi.free.api.dto.SSNValidationResponse;
import fi.free.models.ExchangeRate;
import fi.free.services.ExchangeRateService;

@Validated
@RestController
@RequestMapping(value = "/api")
public class ApiController {

	private final Logger log = LoggerFactory.getLogger(ApiController.class);

	private ExchangeRateService exchangeRateService;

	public ApiController(ExchangeRateService exchangeRateService) {
		this.exchangeRateService = exchangeRateService;
	}

	/*
	 * Endpoint GET /api/currency for exchanging currencies
	 * 
	 * - EUR, SEK and USD need to be supported
	 * 
	 * Request params: - from - to - from_amount
	 * 
	 * Response: - from - to - to_amount - exchange_rate
	 * 
	 * Locally stored exchange rates used
	 * https://apilayer.com/marketplace/description/exchangerates_data-api
	 */
	@GetMapping("/currency")
	public ResponseEntity<ExchangeCurrencyResponse> exchange_amount(
			@RequestParam(name = "from", required = true) @Pattern(regexp = "^EUR|SEK|USD$", flags = {
					Flag.CASE_INSENSITIVE }, message = "Supported currencies EUR, SEK, USD") String from,
			@RequestParam(name = "from_amount", required = true) @Min(value = (long) 0.01) @Max(value = (long) 100000.00) BigDecimal fromAmount,
			@RequestParam(name = "to", required = true) @Pattern(regexp = "^EUR|SEK|USD$", flags = {
					Flag.CASE_INSENSITIVE }, message = "Supported currencies EUR, SEK, USD") String to) {
		log.debug("Exchange currency from {} {} to {}", fromAmount, from, to);
		try {
			ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(from, to);
			BigDecimal result = exchangeRateService.calculateExchangeAmount(fromAmount, exchangeRate.getExchangeValue());
			ExchangeCurrencyResponse response = new ExchangeCurrencyResponse();
			response.setFrom(exchangeRate.getBase());
			response.setTo(exchangeRate.getExchangeRate());
			response.setToAmount(result);
			response.setExchangeRate(fromAmount);			
			return ResponseEntity.ok(response);
		} catch (NoSuchElementException | NumberFormatException e) {
			log.info("Error in exchange rate calculation");
			return ResponseEntity.internalServerError().build();
		}
	}

	/*
	 * Endpoint POST /api/ssn for validating social security numbers
	 * 
	 * JSON body: - ssn - country_code (FI supported currently)
	 * 
	 * Response: - ssn_valid (true/false)
	 * 
	 * SSN validation instructions: https://dvv.fi/en/personal-identity-code
	 */
	@PostMapping("/ssn")
	public ResponseEntity<SSNValidationResponse> validate_ssn(@Valid @RequestBody SSNValidationRequest requestBody) {
		log.debug("Validating {} social security number {}", requestBody.getCountryCode(), requestBody.getSSN());
		// TODO write business logic
		return ResponseEntity.ok(new SSNValidationResponse());
	}

}
