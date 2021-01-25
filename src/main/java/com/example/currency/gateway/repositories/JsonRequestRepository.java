package com.example.currency.gateway.repositories;

import com.example.currency.gateway.domain.JsonRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JsonRequestRepository extends CrudRepository<JsonRequest, UUID> {


    JsonRequest save(JsonRequest jsonRequest);

    Optional<JsonRequest> findById(UUID Id);

    boolean existsById(UUID id);

}
