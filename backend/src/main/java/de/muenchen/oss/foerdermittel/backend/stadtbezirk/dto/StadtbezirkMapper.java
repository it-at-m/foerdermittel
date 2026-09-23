package de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto;

import de.muenchen.oss.foerdermittel.backend.common.NumberMapper;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.StadtbezirkFormContext;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.Listenname;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.StadtbezirkslisteFormContext;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameStadtbezirkslisteFormContextDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = NumberMapper.class)
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface StadtbezirkMapper {

    @Mapping(source = "stadtbezirk", target = "id", qualifiedByName = "bigDecimalToIntegerString")
    @Mapping(source = "stadtbezirk", target = "stadtbezirk")
    StadtbezirkResponseDTO toDTO(Stadtbezirk stadtbezirk);

    Stadtbezirk toEntity(StadtbezirkCreateDTO stadtbezirkCreateDTO);

    @Mapping(target = "stadtbezirk", ignore = true)
    Stadtbezirk toEntity(StadtbezirkUpdateDTO stadtbezirkUpdateDTO);

    StadtbezirkFormContext toFormContext(Stadtbezirk stadtbezirk);

    List<StadtbezirkFormContextDTO> toFormContext(List<Stadtbezirk> stadtbezirkList);

}
