package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto;

import de.muenchen.oss.foerdermittel.backend.common.NumberMapper;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.Listenname;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.StadtbezirkslisteFormContext;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {
                NumberMapper.class,
                ListennameStadtbezirkslisteAssignmentMapper.class
        }
)
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface ListennameStadtbezirkslisteMapper {

    @Mapping(source = "kurzbez", target = "id")
    @Mapping(source = "kurzbez", target = "kurzbez")
    @Mapping(source = "stadtbezirkslisten", target = "assignedStadtbezirke")
    StadtbezirkslisteResponseDTO toDTO(Listenname listenname);

    @Mapping(source = "assignedStadtbezirke", target = "stadtbezirkslisten")
    Listenname toEntity(ListennameCreateDTO listennameCreateDTO);

    @Mapping(target = "kurzbez", ignore = true)
    @Mapping(source = "assignedStadtbezirke", target = "stadtbezirkslisten")
    Listenname toEntity(ListennameUpdateDTO listennameUpdateDTO);

    StadtbezirkslisteFormContext toFormContext(Listenname listenname);

    List<ListennameStadtbezirkslisteFormContextDTO> toFormContext(List<Listenname> listennameList);
}
