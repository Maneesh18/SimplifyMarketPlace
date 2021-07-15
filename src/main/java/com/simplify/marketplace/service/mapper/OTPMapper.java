package com.simplify.marketplace.service.mapper;

import com.simplify.marketplace.domain.*;
import com.simplify.marketplace.service.dto.OTPDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OTP} and its DTO {@link OTPDTO}.
 */
@Mapper(componentModel = "spring", uses = { WorkerMapper.class })
public interface OTPMapper extends EntityMapper<OTPDTO, OTP> {
    @Mapping(target = "worker", source = "worker", qualifiedByName = "id")
    OTPDTO toDto(OTP s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OTPDTO toDtoId(OTP oTP);
}
