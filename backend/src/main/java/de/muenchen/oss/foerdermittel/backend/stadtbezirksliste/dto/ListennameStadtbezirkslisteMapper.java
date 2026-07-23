package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto;

import de.muenchen.oss.foerdermittel.backend.common.NumberMapper;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.Listenname;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
        NumberMapper.class,
        ListennameStadtbezirkslisteAssignmentMapper.class
})
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface ListennameStadtbezirkslisteMapper {

    @Mapping(source = "kurzbez", target = "id")
    @Mapping(source = "kurzbez", target = "kurzbez")
    @Mapping(source = "stadtbezirkslisten", target = "assignedStadtbezirke")
    StadtbezirkslisteResponseDTO toDTO(Listenname listenname);


//    Listenname toEntity(ListennameCreateDTO listennameCreateDTO);
//
//
//    @Mapping(target = "kurzbez", ignore = true)
//    Listenname toEntity(ListennameUpdateDTO listennameUpdateDTO);

}
