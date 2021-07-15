package com.simplify.marketplace.service;

import com.simplify.marketplace.service.dto.PreferredCityDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.simplify.marketplace.domain.PreferredCity}.
 */
public interface PreferredCityService {
    /**
     * Save a preferredCity.
     *
     * @param preferredCityDTO the entity to save.
     * @return the persisted entity.
     */
    PreferredCityDTO save(PreferredCityDTO preferredCityDTO);

    /**
     * Partially updates a preferredCity.
     *
     * @param preferredCityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PreferredCityDTO> partialUpdate(PreferredCityDTO preferredCityDTO);

    /**
     * Get all the preferredCities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PreferredCityDTO> findAll(Pageable pageable);

    /**
     * Get the "id" preferredCity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PreferredCityDTO> findOne(String id);

    /**
     * Delete the "id" preferredCity.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
