package com.simplify.marketplace.repository;

import com.simplify.marketplace.domain.Education;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Education entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EducationRepository extends MongoRepository<Education, String> {}
