package de.muenchen.oss.foerdermittel.backend.bauprogramm.dto;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.Bauprogramm;
import de.muenchen.oss.foerdermittel.backend.common.NumberMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = NumberMapper.class)
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface BauprogrammMapper {

    @Mapping(source = "bauprogramm", target = "id", qualifiedByName = "bigDecimalToIntegerString")
    @Mapping(source = "bauprogramm", target = "bauprogramm")
    BauprogrammResponseDTO toDTO(Bauprogramm bauprogramm);

    Bauprogramm toEntity(BauprogrammCreateDTO bauprogrammCreateDTO);

    @Mapping(target = "bauprogramm", ignore = true)
    Bauprogramm toEntity(BauprogrammUpdateDTO bauprogrammUpdateDTO);

    BauprogrammFormContextDTO toFormContext(Bauprogramm bauprogramm);

    List<BauprogrammFormContextDTO> toFormContext(List<Bauprogramm> bauprogrammList);

}
