package com.simplify.marketplace.repository;

import com.simplify.marketplace.domain.OTP;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the OTP entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OTPRepository extends MongoRepository<OTP, String> {}
