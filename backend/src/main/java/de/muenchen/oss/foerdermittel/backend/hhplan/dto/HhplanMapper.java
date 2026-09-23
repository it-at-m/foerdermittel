package de.muenchen.oss.foerdermittel.backend.hhplan.dto;

import de.muenchen.oss.foerdermittel.backend.common.NumberMapper;
import de.muenchen.oss.foerdermittel.backend.hhplan.Hhplan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(uses = NumberMapper.class)
public interface HhplanMapper {

    @Mapping(source = "hhjJahr", target = "id", qualifiedByName = "bigDecimalToIntegerString")
    @Mapping(source = "hhjJahr", target = "hhjJahr")
    HhplanResponseDTO toDTO(Hhplan hhplan);


    HhplanFormContextDTO toFormContext(Hhplan hhplan);

    List<HhplanFormContextDTO> toFormContext(List<Hhplan> hhplanList);

}
