package de.muenchen.oss.foerdermittel.backend.bauprogramm.dto;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.Bauprogramm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BauprogrammMapper {

    @Mapping(source = "bauprogramm", target = "id")
    @Mapping(source = "bauprogramm", target = "bauprogramm")
    BauprogrammResponseDTO toDTO(Bauprogramm bauprogramm);

    Bauprogramm toEntity(BauprogrammCreateDTO bauprogrammCreateDTO);

    @Mapping(target = "bauprogramm", ignore = true)
    Bauprogramm toEntity(BauprogrammUpdateDTO bauprogrammUpdateDTO);

}
