package de.muenchen.oss.foerdermittel.backend.bauprogramme.dto;

import de.muenchen.oss.foerdermittel.backend.bauprogramme.Bauprogramme;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BauprogrammeMapper {

    BauprogrammeResponseDTO toDTO(Bauprogramme bauprogramm);

    @Mapping(target = "bauprogramm", ignore = true) // Ignoriert die ID bei der Konvertierung
    Bauprogramme toEntity(BauprogrammeRequestDTO bauprogrammRequestDTO);
}
