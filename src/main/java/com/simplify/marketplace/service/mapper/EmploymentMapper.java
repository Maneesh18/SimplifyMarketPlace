package com.simplify.marketplace.service.mapper;

import com.simplify.marketplace.domain.*;
import com.simplify.marketplace.service.dto.EmploymentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employment} and its DTO {@link EmploymentDTO}.
 */
@Mapper(componentModel = "spring", uses = { WorkerMapper.class })
public interface EmploymentMapper extends EntityMapper<EmploymentDTO, Employment> {
    @Mapping(target = "worker", source = "worker", qualifiedByName = "id")
    EmploymentDTO toDto(Employment s);
}
