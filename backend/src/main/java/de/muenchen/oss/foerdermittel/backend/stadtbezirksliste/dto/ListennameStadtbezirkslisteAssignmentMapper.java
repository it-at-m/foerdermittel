package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto;

import de.muenchen.oss.foerdermittel.backend.common.NumberMapper;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.Stadtbezirksliste;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(    componentModel = "spring",uses = NumberMapper.class)
public interface ListennameStadtbezirkslisteAssignmentMapper {
    @Mapping(source = "id.stadtbezirk", target = "stadtbezirkId")
    StadtbezirkslisteAssignmentResponseDTO toDTO(Stadtbezirksliste stadtbezirksliste);
}
