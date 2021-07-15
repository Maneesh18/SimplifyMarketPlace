package com.simplify.marketplace.service.mapper;

import com.simplify.marketplace.domain.*;
import com.simplify.marketplace.service.dto.EducationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Education} and its DTO {@link EducationDTO}.
 */
@Mapper(componentModel = "spring", uses = { WorkerMapper.class })
public interface EducationMapper extends EntityMapper<EducationDTO, Education> {
    @Mapping(target = "worker", source = "worker", qualifiedByName = "id")
    EducationDTO toDto(Education s);
}
