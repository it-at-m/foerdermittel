package de.muenchen.oss.foerdermittel.backend.krankenhaus.dto;

import de.muenchen.oss.foerdermittel.backend.krankenhaus.Krankenhaus;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.Krankenhaus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface KrankenhausMapper {

    @Mapping(source = "krhname", target = "id")
    @Mapping(source = "krhname", target = "krhname")
    KrankenhausResponseDTO toDTO(Krankenhaus krankenhaus);

    Krankenhaus toEntity(KrankenhausCreateDTO krankenhausCreateDTO);

    @Mapping(target = "krhname", ignore = true)
    Krankenhaus toEntity(KrankenhausUpdateDTO krankenhausUpdateDTO);

    KrankenhausFormContextDTO toFormContext(Krankenhaus krankenhaus);

    List<KrankenhausFormContextDTO> toFormContext(List<Krankenhaus> krankenhausList);

}
