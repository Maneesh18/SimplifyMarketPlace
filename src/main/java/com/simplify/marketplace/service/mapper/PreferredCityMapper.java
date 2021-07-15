package com.simplify.marketplace.service.mapper;

import com.simplify.marketplace.domain.*;
import com.simplify.marketplace.service.dto.PreferredCityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PreferredCity} and its DTO {@link PreferredCityDTO}.
 */
@Mapper(componentModel = "spring", uses = { JobPreferenceMapper.class })
public interface PreferredCityMapper extends EntityMapper<PreferredCityDTO, PreferredCity> {
    @Mapping(target = "jobPreference", source = "jobPreference", qualifiedByName = "id")
    PreferredCityDTO toDto(PreferredCity s);
}
