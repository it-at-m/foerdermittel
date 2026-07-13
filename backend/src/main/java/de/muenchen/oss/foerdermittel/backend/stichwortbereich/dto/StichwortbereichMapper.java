package de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto;

import de.muenchen.oss.foerdermittel.backend.stichwortbereich.Stichwortbereich;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface StichwortbereichMapper {

    @Mapping(source="bereich", target = "id")
    @Mapping(source="bereich", target = "bereich")
    StichwortbereichResponseDTO toDTO(Stichwortbereich stichwortbereich);

    Stichwortbereich toEntity(StichwortbereichCreateDTO stichwortbereichCreateDTO);

    @Mapping(target="bereich", ignore = true)
    Stichwortbereich toEntity(StichwortbereichUpdateDTO stichwortbereichUpdateDTO);
}
