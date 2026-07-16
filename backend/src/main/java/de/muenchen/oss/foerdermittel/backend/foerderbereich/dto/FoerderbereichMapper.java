package de.muenchen.oss.foerdermittel.backend.foerderbereich.dto;

import de.muenchen.oss.foerdermittel.backend.common.NumberMapper;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = NumberMapper.class)
public interface FoerderbereichMapper {

    @Mapping(source = "fb", target = "id", qualifiedByName = "bigDecimalToIntegerString")
    @Mapping(source = "fb", target = "fb")
    FoerderbereichResponseDTO toDTO(Foerderbereich foerderbereich);

    Foerderbereich toEntity(FoerderbereichCreateDTO foerderbereichCreateDTO);

    @Mapping(target = "fb", ignore = true)
    Foerderbereich toEntity(FoerderbereichUpdateDTO foerderbereichUpdateDTO);

}
