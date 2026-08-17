package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProjektMapper {

    @Mapping(source = "foerderbereich.fb", target = "foerderbereich")
    ProjektResponseDTO toDTO(Projekt projekt);
}
