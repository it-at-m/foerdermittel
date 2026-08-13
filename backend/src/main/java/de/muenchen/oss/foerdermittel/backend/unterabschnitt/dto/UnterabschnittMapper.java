package de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto;

import de.muenchen.oss.foerdermittel.backend.unterabschnitt.Unterabschnitt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UnterabschnittMapper {

    @Mapping(source = "ua", target = "id")
    @Mapping(source = "ua", target = "ua")
    @Mapping(source = "hasHa.ha", target = "hasHa")
    UnterabschnittResponseDTO toDTO(Unterabschnitt unterabschnitt);

    @Mapping(source = "hasHa", target = "hasHa.ha")
    Unterabschnitt toEntity(UnterabschnittCreateDTO unterabschnittCreateDTO);

    @Mapping(source = "hasHa", target = "hasHa.ha")
    @Mapping(target = "ua", ignore = true)
    Unterabschnitt toEntity(UnterabschnittUpdateDTO unterabschnittUpdateDTO);

}
