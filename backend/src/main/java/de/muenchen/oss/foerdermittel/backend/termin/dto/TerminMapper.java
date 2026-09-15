package de.muenchen.oss.foerdermittel.backend.termin.dto;

import de.muenchen.oss.foerdermittel.backend.common.TimeZoneMapper;
import de.muenchen.oss.foerdermittel.backend.termin.Termin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TerminMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "projekt.projnr", target = "projnr")
    @Mapping(source = "projekt.pname", target = "pname")
    @Mapping(source = "projekt.pstrasse", target = "pstrasse")
    @Mapping(source = "projekt.foerderbereich.fb", target = "fob_fb")
    @Mapping(source = "projekt.stadtbezirk.stadtbezirk", target = "bez_stadtbezirk")
    TerminResponseDTO toDTO(Termin termin);

    @Mapping(source = "projnr", target = "projekt.projnr")
    Termin toEntity(TerminCreateDTO terminCreateDTO);

    @Mapping(target = "projekt", ignore = true)
    @Mapping(target = "id", ignore = true)
    Termin toEntity(TerminUpdateDTO terminUpdateDTO);
}
