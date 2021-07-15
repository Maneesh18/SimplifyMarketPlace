package com.simplify.marketplace.service.impl;

import com.simplify.marketplace.domain.OTP;
import com.simplify.marketplace.repository.OTPRepository;
import com.simplify.marketplace.service.OTPService;
import com.simplify.marketplace.service.dto.OTPDTO;
import com.simplify.marketplace.service.mapper.OTPMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link OTP}.
 */
@Service
public class OTPServiceImpl implements OTPService {

    private final Logger log = LoggerFactory.getLogger(OTPServiceImpl.class);

    private final OTPRepository oTPRepository;

    private final OTPMapper oTPMapper;

    public OTPServiceImpl(OTPRepository oTPRepository, OTPMapper oTPMapper) {
        this.oTPRepository = oTPRepository;
        this.oTPMapper = oTPMapper;
    }

    @Override
    public OTPDTO save(OTPDTO oTPDTO) {
        log.debug("Request to save OTP : {}", oTPDTO);
        OTP oTP = oTPMapper.toEntity(oTPDTO);
        oTP = oTPRepository.save(oTP);
        return oTPMapper.toDto(oTP);
    }

    @Override
    public Optional<OTPDTO> partialUpdate(OTPDTO oTPDTO) {
        log.debug("Request to partially update OTP : {}", oTPDTO);

        return oTPRepository
            .findById(oTPDTO.getId())
            .map(
                existingOTP -> {
                    oTPMapper.partialUpdate(existingOTP, oTPDTO);

                    return existingOTP;
                }
            )
            .map(oTPRepository::save)
            .map(oTPMapper::toDto);
    }

    @Override
    public Page<OTPDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OTPS");
        return oTPRepository.findAll(pageable).map(oTPMapper::toDto);
    }

    @Override
    public Optional<OTPDTO> findOne(String id) {
        log.debug("Request to get OTP : {}", id);
        return oTPRepository.findById(id).map(oTPMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete OTP : {}", id);
        oTPRepository.deleteById(id);
    }
}
