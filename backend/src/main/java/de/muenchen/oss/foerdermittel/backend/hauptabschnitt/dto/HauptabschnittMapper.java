package de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto;

import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.Hauptabschnitt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface HauptabschnittMapper {

    @Mapping(source = "ha", target = "id")
    @Mapping(source = "ha", target = "ha")
    HauptabschnittResponseDTO toDTO(Hauptabschnitt hauptabschnitt);

    Hauptabschnitt toEntity(HauptabschnittCreateDTO hauptabschnittCreateDTO);

    @Mapping(target = "ha", ignore = true)
    Hauptabschnitt toEntity(HauptabschnittUpdateDTO hauptabschnittUpdateDTO);

    HauptabschnittFormContextDTO toFormContext(Hauptabschnitt hauptabschnitt);

    List<HauptabschnittFormContextDTO> toFormContext(List<Hauptabschnitt> hauptabschnittList);

}
