package com.simplify.marketplace.service.impl;

import com.simplify.marketplace.domain.PreferredCity;
import com.simplify.marketplace.repository.PreferredCityRepository;
import com.simplify.marketplace.service.PreferredCityService;
import com.simplify.marketplace.service.dto.PreferredCityDTO;
import com.simplify.marketplace.service.mapper.PreferredCityMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link PreferredCity}.
 */
@Service
public class PreferredCityServiceImpl implements PreferredCityService {

    private final Logger log = LoggerFactory.getLogger(PreferredCityServiceImpl.class);

    private final PreferredCityRepository preferredCityRepository;

    private final PreferredCityMapper preferredCityMapper;

    public PreferredCityServiceImpl(PreferredCityRepository preferredCityRepository, PreferredCityMapper preferredCityMapper) {
        this.preferredCityRepository = preferredCityRepository;
        this.preferredCityMapper = preferredCityMapper;
    }

    @Override
    public PreferredCityDTO save(PreferredCityDTO preferredCityDTO) {
        log.debug("Request to save PreferredCity : {}", preferredCityDTO);
        PreferredCity preferredCity = preferredCityMapper.toEntity(preferredCityDTO);
        preferredCity = preferredCityRepository.save(preferredCity);
        return preferredCityMapper.toDto(preferredCity);
    }

    @Override
    public Optional<PreferredCityDTO> partialUpdate(PreferredCityDTO preferredCityDTO) {
        log.debug("Request to partially update PreferredCity : {}", preferredCityDTO);

        return preferredCityRepository
            .findById(preferredCityDTO.getId())
            .map(
                existingPreferredCity -> {
                    preferredCityMapper.partialUpdate(existingPreferredCity, preferredCityDTO);

                    return existingPreferredCity;
                }
            )
            .map(preferredCityRepository::save)
            .map(preferredCityMapper::toDto);
    }

    @Override
    public Page<PreferredCityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PreferredCities");
        return preferredCityRepository.findAll(pageable).map(preferredCityMapper::toDto);
    }

    @Override
    public Optional<PreferredCityDTO> findOne(String id) {
        log.debug("Request to get PreferredCity : {}", id);
        return preferredCityRepository.findById(id).map(preferredCityMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete PreferredCity : {}", id);
        preferredCityRepository.deleteById(id);
    }
}
