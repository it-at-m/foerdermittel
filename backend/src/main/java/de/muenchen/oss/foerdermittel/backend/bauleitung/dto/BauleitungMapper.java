package de.muenchen.oss.foerdermittel.backend.bauleitung.dto;

import de.muenchen.oss.foerdermittel.backend.bauleitung.Bauleitung;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BauleitungMapper {

    BauleitungResponseDTO toDTO(Bauleitung bauleitung);

    @Mapping(target = "bauleitung", ignore = true) // Ignoriert die ID bei der Konvertierung
    Bauleitung toEntity(BauleitungRequestDTO bauleitungRequestDTO);
}