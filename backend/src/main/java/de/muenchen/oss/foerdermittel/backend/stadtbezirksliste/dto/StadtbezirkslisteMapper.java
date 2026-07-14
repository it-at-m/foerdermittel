package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto;

import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.Stadtbezirksliste;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.StadtbezirkslisteCreateDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.StadtbezirkslisteResponseDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.StadtbezirkslisteUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface StadtbezirkslisteMapper {

    @Mapping(source = "lnaKurzbez", target = "id")
    @Mapping(source = "lnaKurzbez", target = "lnaKurzbez")
    StadtbezirkslisteResponseDTO toDTO(Stadtbezirksliste stadtbezirksliste);

    Stadtbezirksliste toEntity(StadtbezirkslisteCreateDTO stadtbezirkslisteCreateDTO);

    @Mapping(target = "lnaKurzbez", ignore = true)
    Stadtbezirksliste toEntity(StadtbezirkslisteUpdateDTO stadtbezirkslisteUpdateDTO);

}
