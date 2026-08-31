package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto;

import de.muenchen.oss.foerdermittel.backend.common.NumberMapper;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.Stadtbezirksliste;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = NumberMapper.class
)
public interface ListennameStadtbezirkslisteAssignmentMapper {

    @Mapping(source = "id.stadtbezirk", target = "stadtbezirkId")
    @Mapping(source = "stadtbezirk.bezeichnung", target = "stadtbezirkBezeichnung")
    StadtbezirkslisteAssignmentResponseDTO toDTO(Stadtbezirksliste entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "listenName", ignore = true)
    @Mapping(target = "stadtbezirk.stadtbezirk", source = "stadtbezirkId")
    @Mapping(target = "stadtbezirk.bezeichnung", ignore = true)
    Stadtbezirksliste toEntity(StadtbezirkslisteAssignmentResponseDTO dto);
}
