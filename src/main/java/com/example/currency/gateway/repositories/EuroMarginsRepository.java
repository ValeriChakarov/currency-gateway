package com.example.currency.gateway.repositories;

import com.example.currency.gateway.domain.EuroMargins;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EuroMarginsRepository extends CrudRepository<EuroMargins, UUID> {

    EuroMargins save(EuroMargins euroRates);

    Optional<EuroMargins> findById(UUID Id);
}
