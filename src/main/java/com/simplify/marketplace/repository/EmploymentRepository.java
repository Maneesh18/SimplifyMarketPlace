package com.simplify.marketplace.repository;

import com.simplify.marketplace.domain.Employment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Employment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmploymentRepository extends MongoRepository<Employment, String> {}
