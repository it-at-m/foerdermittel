package de.muenchen.oss.foerdermittel.backend.listenname.dto;

import de.muenchen.oss.foerdermittel.backend.listenname.Listenname;
import de.muenchen.oss.foerdermittel.backend.listenname.dto.ListennameCreateDTO;
import de.muenchen.oss.foerdermittel.backend.listenname.dto.ListennameResponseDTO;
import de.muenchen.oss.foerdermittel.backend.listenname.dto.ListennameUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface ListennameMapper {

    @Mapping(source = "kurzbez", target = "id")
    @Mapping(source = "kurzbez", target = "kurzbez")
    ListennameResponseDTO toDTO(Listenname listenname);

    Listenname toEntity(ListennameCreateDTO listennameCreateDTO);

    @Mapping(target = "kurzbez", ignore = true)
    Listenname toEntity(ListennameUpdateDTO listennameUpdateDTO);

}
