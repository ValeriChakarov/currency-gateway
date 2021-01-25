package com.example.currency.gateway.repositories;

import com.example.currency.gateway.domain.EuroRates;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EuroRatesRepository extends CrudRepository<EuroRates, UUID> {

    EuroRates save(EuroRates euroRates);

    Optional<EuroRates> findById(UUID Id);

    @Query("select e from EuroRates e WHERE e.timestamp = (SELECT MAX(e.timestamp) FROM EuroRates e)")
    Optional<EuroRates> getCurrentEuroRates();

    @Query("SELECT e FROM EuroRates e WHERE e.timestamp BETWEEN :startTime AND :endTime")
    List<EuroRates> getHistoricalEuroRates(@Param("startTime") Instant startTime, @Param("endTime") Instant endTime);
}
