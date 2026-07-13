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

    @Mapping(source = "lna_kurzbez", target = "id")
    @Mapping(source = "lna_kurzbez", target = "lna_kurzbez")
    StadtbezirkslisteResponseDTO toDTO(Stadtbezirksliste stadtbezirksliste);

    Stadtbezirksliste toEntity(StadtbezirkslisteCreateDTO stadtbezirkslisteCreateDTO);

    @Mapping(target = "lna_kurzbez", ignore = true)
    Stadtbezirksliste toEntity(StadtbezirkslisteUpdateDTO stadtbezirkslisteUpdateDTO);

}
