package fi.free.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fi.free.models.ExchangeRate;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

	@Query("SELECT ex FROM ExchangeRate ex WHERE ex.base = ?1 AND ex.exchangeRate = ?2")
	Optional<ExchangeRate> findExchangeRate(String from, String to);
}
