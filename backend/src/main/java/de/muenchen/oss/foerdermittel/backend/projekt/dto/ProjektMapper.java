package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjektMapper {

    ProjektResponseDTO toDTO(Projekt projekt);
}