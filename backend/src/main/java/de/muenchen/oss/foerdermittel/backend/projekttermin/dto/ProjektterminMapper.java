package de.muenchen.oss.foerdermittel.backend.projekttermin.dto;

import de.muenchen.oss.foerdermittel.backend.common.TimeZoneMapper;
import de.muenchen.oss.foerdermittel.backend.projekttermin.Projekttermin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = TimeZoneMapper.class)
public interface ProjektterminMapper {

    String TERMIN = "termin";
    String OFFSET_TO_LOCAL_DATE = "offSetDateTimeToLocalDate";
    String LOCAL_DATE_TO_OFFSET = "localDateToOffSetDate";

    @Mapping(source = "id", target = "id")
    @Mapping(source = "projekt.projnr", target = "projnr")
    @Mapping(source = "projekt.pname", target = "pname")
    @Mapping(source = "projekt.pstrasse", target = "pstrasse")
    @Mapping(source = "projekt.foerderbereich.fb", target = "fob_fb")
    @Mapping(source = "projekt.stadtbezirk.stadtbezirk", target = "bez_stadtbezirk")
    @Mapping(
            source = TERMIN,
            target = TERMIN,
            qualifiedByName = LOCAL_DATE_TO_OFFSET
    )
    ProjektterminResponseDTO toDTO(Projekttermin projekttermin);

    @Mapping(source = "projnr", target = "projekt.projnr")
    @Mapping(
            source = TERMIN,
            target = TERMIN,
            qualifiedByName = OFFSET_TO_LOCAL_DATE
    )
    Projekttermin toEntity(ProjektterminCreateDTO projektterminCreateDTO);

    @Mapping(target = "projekt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(
            source = TERMIN,
            target = TERMIN,
            qualifiedByName = OFFSET_TO_LOCAL_DATE
    )
    Projekttermin toEntity(ProjektterminUpdateDTO projektterminUpdateDTO);
}
