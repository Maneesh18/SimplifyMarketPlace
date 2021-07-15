package com.simplify.marketplace.repository;

import java.util.Optional;
import com.simplify.marketplace.domain.Worker;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Worker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkerRepository extends MongoRepository<Worker, String> {
    Optional<Worker> findOneByEmailIgnoreCase(String email);
}
