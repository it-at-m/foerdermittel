package de.muenchen.oss.foerdermittel.backend.benutzerhinweis.dto;

import de.muenchen.oss.foerdermittel.backend.benutzerhinweis.Benutzerhinweis;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface BenutzerhinweisMapper {

    @Mapping(source = "viewId", target = "id")
    @Mapping(source = "viewId", target = "viewId")
    BenutzerhinweisResponseDTO toDTO(Benutzerhinweis benutzerhinweis);

    Benutzerhinweis toEntity(BenutzerhinweisCreateDTO benutzerhinweisCreateDTO);

    @Mapping(target = "viewId", ignore = true)
    Benutzerhinweis toEntity(BenutzerhinweisUpdateDTO benutzerhinweisUpdateDTO);

}
