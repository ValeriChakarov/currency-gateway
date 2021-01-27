package com.example.currency.gateway.repositories;

import com.example.currency.gateway.domain.XmlRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface XmlRequestRepository extends CrudRepository<XmlRequest, String> {

    XmlRequest save(XmlRequest jsonRequest);

    Optional<XmlRequest> findById(String Id);

    boolean existsById(String id);
}
