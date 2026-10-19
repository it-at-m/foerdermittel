package de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto;

import de.muenchen.oss.foerdermittel.backend.unterabschnitt.Unterabschnitt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface UnterabschnittMapper {

    @Mapping(source = "ua", target = "id")
    @Mapping(source = "ua", target = "ua")
    @Mapping(source = "hasHa.ha", target = "hasHa")
    @Mapping(source = "hasHa.bezeichnung", target = "haBezeichnung")
    UnterabschnittResponseDTO toDTO(Unterabschnitt unterabschnitt);

    @Mapping(target = "hasHa", ignore = true)
    Unterabschnitt toEntity(UnterabschnittCreateDTO unterabschnittCreateDTO);

    @Mapping(target = "hasHa", ignore = true)
    @Mapping(target = "ua", ignore = true)
    Unterabschnitt toEntity(UnterabschnittUpdateDTO unterabschnittUpdateDTO);

    UnterabschnittFormContextDTO toFormContext(Unterabschnitt unterabschnitt);

    List<UnterabschnittFormContextDTO> toFormContext(List<Unterabschnitt> unterabschnittList);

}
