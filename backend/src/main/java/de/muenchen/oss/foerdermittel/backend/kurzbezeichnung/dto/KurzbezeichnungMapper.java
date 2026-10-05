package de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto;

import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.Kurzbezeichnung;
import java.util.List;
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

    KurzbezeichnungFormContextDTO toFormContext(Kurzbezeichnung kurzbezeichnung);

    List<KurzbezeichnungFormContextDTO> toFormContext(List<Kurzbezeichnung> kurzbezeichnungList);

}
