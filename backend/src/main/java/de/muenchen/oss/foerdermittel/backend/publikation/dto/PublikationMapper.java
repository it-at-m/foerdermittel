package de.muenchen.oss.foerdermittel.backend.publikation.dto;

import de.muenchen.oss.foerdermittel.backend.publikation.Publikation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface PublikationMapper {

    @Mapping(source = "kurzform", target = "id")
    @Mapping(source = "kurzform", target = "kurzform")
    PublikationResponseDTO toDTO(Publikation publikation);

    Publikation toEntity(PublikationCreateDTO publikationCreateDTO);

    @Mapping(target = "kurzform", ignore = true)
    Publikation toEntity(PublikationUpdateDTO publikationUpdateDTO);

}
