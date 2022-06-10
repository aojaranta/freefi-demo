package fi.free.apilayer;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import fi.free.api.controllers.ApiController;
import fi.free.models.ExchangeRate;
import fi.free.repositories.ExchangeRateRepository;

@Service
public class ApiLayerService {

	private final Logger log = LoggerFactory.getLogger(ApiController.class);

	private final String apilayer_apikey;
	private final String apilayer_base_url = "https://api.apilayer.com";
	private final String apilayer_latest_url = apilayer_base_url + "/exchangerates_data/latest";

	private final ExchangeRateRepository exchangeRateRepository;

	public ApiLayerService(@Value("${fi.free.apilayer.apiKey}") String apiLayerApiKey,
			ExchangeRateRepository exchangeRateRepository) {
		this.apilayer_apikey = apiLayerApiKey;
		this.exchangeRateRepository = exchangeRateRepository;
	}

	/**
	 * Gets latest exchange rates for currencies and stores them to database.
	 * 
	 * @param base    Currency code used to as base currency
	 * @param symbols All currency codes that must be included in the exchange rate
	 *                result
	 */
	public void getLatestExchangeRatesFromApiLayer(String base, String[] symbols) {
		log.info("Get latest exchange rates for currency {}", base);
		ExchangeRateResponse response = null;
		try {
			response = fetchLatestExchangeRates(base, symbols);
		} catch (RestClientException e) {
			log.warn("Error while fetching new exchange rates. Reason: {}", e.getMessage());
			// This block is meant to be backup to simulate real SUCCESSFULL response from apilayer...which seems to be constantly exceeding the usage limits.
			response = loadLocalExchangeRateResponse(base.toLowerCase() + ".json");
			log.debug("{} {} {}", response.getBase(), response.getDate(), response.getRates().toString());
		}
		if(response == null)
			return;
		Set<ExchangeRate> exchangeRates = convertExchangeRateResponseToExchangeRates(response);
		exchangeRateRepository.deleteAllByBase(base);
		exchangeRateRepository.saveAll(exchangeRates);
		exchangeRateRepository.flush();
	}

	private ExchangeRateResponse loadLocalExchangeRateResponse(String fileName) {
		log.info("Loading backup template file {}", fileName);
		Resource templateFile = new ClassPathResource(fileName);
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(templateFile.getInputStream(), ExchangeRateResponse.class);
		} catch (IOException e) {
			log.warn("Failed to load local ExchangeRate template file");
		}
		return null;
	}

	/**
	 * Converts ExchangeRateResponse to ExchangeRate models
	 * 
	 * @param response
	 * @return Set<ExchangeRate>
	 */
	private Set<ExchangeRate> convertExchangeRateResponseToExchangeRates(ExchangeRateResponse response) {
		Set<ExchangeRate> exchangeRates = new HashSet<ExchangeRate>();
		response.getRates().forEach((key, value) -> {
			ExchangeRate exchangeRate = new ExchangeRate();
			exchangeRate.setBase(response.getBase());
			exchangeRate.setDate(response.getDate());
			exchangeRate.setExchangeRate(key);
			exchangeRate.setExchangeValue(value);
			exchangeRate.setTimestamp(response.getTimestamp());
			exchangeRates.add(exchangeRate);
		});
		return exchangeRates;
	}

	/**
	 * Fetches latest exchange rate from ApiLayer service
	 * 
	 * @param base    Base currency
	 * @param symbols Exchange rate currencies
	 * @return ExchangeRateResponse Latest exchange rate data
	 */
	private ExchangeRateResponse fetchLatestExchangeRates(String base, String[] symbols) throws RestClientException {
		log.info("Sending HTTP request to ApiLayer URL {}", apilayer_latest_url);
		RestTemplate request = new RestTemplate();
		final HttpHeaders headers = new HttpHeaders();
		headers.set("apikey", apilayer_apikey);
		final HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map<String, String> queryParams = new HashMap<>();
		queryParams.put("base", base);
		queryParams.put("symbols", symbols.toString());
		ResponseEntity<ExchangeRateResponse> response = null;
		response = request.exchange(apilayer_latest_url, HttpMethod.GET, entity, ExchangeRateResponse.class,
				queryParams);
		log.info("Response from ApiLayer {} {}", response.getStatusCodeValue(), response.getStatusCode());
		return response.getBody();
	}

}
