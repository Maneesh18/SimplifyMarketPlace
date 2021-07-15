package com.simplify.marketplace.service.mapper;

import com.simplify.marketplace.domain.*;
import com.simplify.marketplace.service.dto.OTPAttemptDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OTPAttempt} and its DTO {@link OTPAttemptDTO}.
 */
@Mapper(componentModel = "spring", uses = { OTPMapper.class })
public interface OTPAttemptMapper extends EntityMapper<OTPAttemptDTO, OTPAttempt> {
    @Mapping(target = "otp", source = "otp", qualifiedByName = "id")
    OTPAttemptDTO toDto(OTPAttempt s);
}
