package de.muenchen.oss.foerdermittel.backend.archiv.dto;

import de.muenchen.oss.foerdermittel.backend.archiv.Archiv;
import de.muenchen.oss.foerdermittel.backend.common.TimeZoneMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = TimeZoneMapper.class)
public interface ArchivMapper {

    String SPEICHER_DATUM = "speicherDatum";
    String MIKRO_DAT_PLAN = "mikroDatPlan";
    String MIKRO_DAT = "mikroDat";
    String OFFSET_TO_LOCAL_DATE = "offSetDateTimeToLocalDate";
    String LOCAL_DATE_TO_OFFSET = "localDateToOffSetDate";

    @Mapping(source = "id", target = "id")
    @Mapping(source = "projekt.projnr", target = "projnr")
    @Mapping(source = "projekt.pname", target = "pname")
    @Mapping(source = "projekt.pstrasse", target = "pstrasse")
    @Mapping(source = "projekt.foerderbereich.fb", target = "fob_fb")
    @Mapping(
            source = SPEICHER_DATUM,
            target = SPEICHER_DATUM,
            qualifiedByName = LOCAL_DATE_TO_OFFSET
    )
    @Mapping(
            source = MIKRO_DAT_PLAN,
            target = MIKRO_DAT_PLAN,
            qualifiedByName = LOCAL_DATE_TO_OFFSET
    )
    @Mapping(
            source = MIKRO_DAT,
            target = MIKRO_DAT,
            qualifiedByName = LOCAL_DATE_TO_OFFSET
    )
    ArchivResponseDTO toDTO(Archiv archiv);

    @Mapping(source = "projnr", target = "projekt.projnr")
    @Mapping(
            source = SPEICHER_DATUM,
            target = SPEICHER_DATUM,
            qualifiedByName = OFFSET_TO_LOCAL_DATE
    )
    @Mapping(
            source = MIKRO_DAT_PLAN,
            target = MIKRO_DAT_PLAN,
            qualifiedByName = OFFSET_TO_LOCAL_DATE
    )
    @Mapping(
            source = MIKRO_DAT,
            target = MIKRO_DAT,
            qualifiedByName = OFFSET_TO_LOCAL_DATE
    )
    Archiv toEntity(ArchivCreateDTO archivCreateDTO);

    @Mapping(target = "projekt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(
            source = SPEICHER_DATUM,
            target = SPEICHER_DATUM,
            qualifiedByName = OFFSET_TO_LOCAL_DATE
    )
    @Mapping(
            source = MIKRO_DAT_PLAN,
            target = MIKRO_DAT_PLAN,
            qualifiedByName = OFFSET_TO_LOCAL_DATE
    )
    @Mapping(
            source = MIKRO_DAT,
            target = MIKRO_DAT,
            qualifiedByName = OFFSET_TO_LOCAL_DATE
    )
    Archiv toEntity(ArchivUpdateDTO archivUpdateDTO);

}
