package de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto;

import de.muenchen.oss.foerdermittel.backend.common.NumberMapper;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = NumberMapper.class)
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface StadtbezirkMapper {

    @Mapping(source = "stadtbezirk", target = "id", qualifiedByName = "bigDecimalToIntegerString")
    @Mapping(source = "stadtbezirk", target = "stadtbezirk")
    StadtbezirkResponseDTO toDTO(Stadtbezirk stadtbezirk);

    Stadtbezirk toEntity(StadtbezirkCreateDTO stadtbezirkCreateDTO);

    @Mapping(target = "stadtbezirk", ignore = true)
    Stadtbezirk toEntity(StadtbezirkUpdateDTO stadtbezirkUpdateDTO);

}
