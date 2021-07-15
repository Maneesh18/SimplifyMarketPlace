package com.simplify.marketplace.repository;

import com.simplify.marketplace.domain.PreferredCity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the PreferredCity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreferredCityRepository extends MongoRepository<PreferredCity, String> {}
