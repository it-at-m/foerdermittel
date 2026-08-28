package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.Hauptabschnitt;
import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto.HauptabschnittUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.Krankenhaus;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausCreateDTO;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausUpdateDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjektMapper {

    ProjektResponseDTO toDTO(Projekt projekt);

    Projekt toEntity(ProjektCreateDTO projektCreateDTO);

    @Mapping(target = "projnr", ignore = true)
    Projekt toEntity(ProjektUpdateDTO projektUpdateDTO);
}
