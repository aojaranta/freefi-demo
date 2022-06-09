package fi.free.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fi.free.models.ExchangeRate;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

}
