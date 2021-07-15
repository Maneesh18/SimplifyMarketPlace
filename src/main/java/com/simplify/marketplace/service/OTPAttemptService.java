package com.simplify.marketplace.service;

import com.simplify.marketplace.service.dto.OTPAttemptDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.simplify.marketplace.domain.OTPAttempt}.
 */
public interface OTPAttemptService {
    /**
     * Save a oTPAttempt.
     *
     * @param oTPAttemptDTO the entity to save.
     * @return the persisted entity.
     */
    OTPAttemptDTO save(OTPAttemptDTO oTPAttemptDTO);

    /**
     * Partially updates a oTPAttempt.
     *
     * @param oTPAttemptDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OTPAttemptDTO> partialUpdate(OTPAttemptDTO oTPAttemptDTO);

    /**
     * Get all the oTPAttempts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OTPAttemptDTO> findAll(Pageable pageable);

    /**
     * Get the "id" oTPAttempt.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OTPAttemptDTO> findOne(String id);

    /**
     * Delete the "id" oTPAttempt.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
