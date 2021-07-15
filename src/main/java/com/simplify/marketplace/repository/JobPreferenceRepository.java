package com.simplify.marketplace.repository;

import com.simplify.marketplace.domain.JobPreference;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the JobPreference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobPreferenceRepository extends MongoRepository<JobPreference, String> {}
