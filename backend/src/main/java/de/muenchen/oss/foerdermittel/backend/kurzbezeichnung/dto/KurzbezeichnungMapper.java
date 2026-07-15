package de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto;

import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.Kurzbezeichnung;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface KurzbezeichnungMapper {

    @Mapping(source = "kurzbez", target = "id")
    @Mapping(source = "kurzbez", target = "kurzbez")
    KurzbezeichnungResponseDTO toDTO(Kurzbezeichnung kurzbezeichnung);

    Kurzbezeichnung toEntity(KurzbezeichnungCreateDTO kurzbezeichnungCreateDTO);

    @Mapping(target = "kurzbez", ignore = true)
    Kurzbezeichnung toEntity(KurzbezeichnungUpdateDTO kurzbezeichnungUpdateDTO);

}
