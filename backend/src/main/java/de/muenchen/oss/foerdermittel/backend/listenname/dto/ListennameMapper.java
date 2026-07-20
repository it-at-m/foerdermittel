package de.muenchen.oss.foerdermittel.backend.listenname.dto;

import de.muenchen.oss.foerdermittel.backend.listenname.Listenname;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ListennameMapper {

    @Mapping(source = "kurzbez", target = "id")
    ListennameResponseDTO toDTO(Listenname listenname);


    Listenname toEntity(ListennameCreateDTO listennameCreateDTO);


    @Mapping(target = "kurzbez", ignore = true)
    Listenname toEntity(ListennameUpdateDTO listennameUpdateDTO);

}