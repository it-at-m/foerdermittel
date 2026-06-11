package de.muenchen.oss.foerdermittel.backend.bauprogramm.dto;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.Bauprogramm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BauprogrammMapper {

    @Mapping(source = "bauprogramm", target = "id") // Mappt 'bauprogramm' auf 'id'
    @Mapping(source = "bauprogramm", target = "bauprogramm")
    BauprogrammResponseDTO toDTO(Bauprogramm bauprogramm);

    @Mapping(source = "id", target = "bauprogramm") // Mappt 'id' auf 'bauprogramm'
    Bauprogramm toEntity(BauprogrammRequestDTO bauprogrammRequestDTO);
}
