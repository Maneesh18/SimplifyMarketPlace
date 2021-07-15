package com.simplify.marketplace.repository;

import com.simplify.marketplace.domain.OTPAttempt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the OTPAttempt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OTPAttemptRepository extends MongoRepository<OTPAttempt, String> {}
