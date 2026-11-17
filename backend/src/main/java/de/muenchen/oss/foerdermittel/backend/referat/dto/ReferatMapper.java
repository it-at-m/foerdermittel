package de.muenchen.oss.foerdermittel.backend.referat.dto;

import de.muenchen.oss.foerdermittel.backend.common.NumberMapper;
import de.muenchen.oss.foerdermittel.backend.referat.Referat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = NumberMapper.class)
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface ReferatMapper {

    @Mapping(source = "refnr", target = "id", qualifiedByName = "bigDecimalToIntegerString")
    @Mapping(source = "refnr", target = "refnr")
    ReferatResponseDTO toDTO(Referat referat);

    Referat toEntity(ReferatCreateDTO referatCreateDTO);

    @Mapping(target = "refnr", ignore = true)
    Referat toEntity(ReferatUpdateDTO referatUpdateDTO);

}
