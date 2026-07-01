package de.muenchen.oss.foerdermittel.backend.traeger.dto;

import de.muenchen.oss.foerdermittel.backend.common.NumberMapper;
import de.muenchen.oss.foerdermittel.backend.traeger.Traeger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = NumberMapper.class)
public interface TraegerMapper {

    @Mapping(source = "kurzform", target = "id", qualifiedByName = "bigDecimalToIntegerString")
    @Mapping(source = "kurzform", target = "kurzform")
    TraegerResponseDTO toDTO(Traeger traeger);

    Traeger toEntity(TraegerCreateDTO traegerCreateDTO);

    @Mapping(target = "kurzform", ignore = true)
    Traeger toEntity(TraegerUpdateDTO traegerUpdateDTO);

}
