package com.simplify.marketplace.service.mapper;

import com.simplify.marketplace.domain.*;
import com.simplify.marketplace.service.dto.JobPreferenceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link JobPreference} and its DTO {@link JobPreferenceDTO}.
 */
@Mapper(componentModel = "spring", uses = { WorkerMapper.class })
public interface JobPreferenceMapper extends EntityMapper<JobPreferenceDTO, JobPreference> {
    @Mapping(target = "worker", source = "worker", qualifiedByName = "id")
    JobPreferenceDTO toDto(JobPreference s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    JobPreferenceDTO toDtoId(JobPreference jobPreference);
}
