package de.muenchen.oss.foerdermittel.backend.bauleitung.dto;

import de.muenchen.oss.foerdermittel.backend.bauleitung.Bauleitung;
import de.muenchen.oss.foerdermittel.backend.common.NumberMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = NumberMapper.class)
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface BauleitungMapper {

    @Mapping(source = "bauleitung", target = "id")
    @Mapping(source = "bauleitung", target = "bauleitung")
    BauleitungResponseDTO toDTO(Bauleitung bauleitung);

    Bauleitung toEntity(BauleitungCreateDTO bauleitungCreateDTO);

    @Mapping(target = "bauleitung", ignore = true)
    Bauleitung toEntity(BauleitungUpdateDTO bauleitungUpdateDTO);

}
