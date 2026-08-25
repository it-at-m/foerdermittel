package de.muenchen.oss.foerdermittel.backend.archiv.dto;

import de.muenchen.oss.foerdermittel.backend.archiv.Archiv;
import de.muenchen.oss.foerdermittel.backend.common.TimeZoneMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = TimeZoneMapper.class)
public interface ArchivMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "projekt.projnr", target = "projnr")
    @Mapping(source = "projekt.pname", target = "pname")
    @Mapping(source = "projekt.pstrasse", target = "pstrasse")
    @Mapping(source = "projekt.foerderbereich.fb", target = "fob_fb")
    @Mapping(
            source = "speicherDatum",
            target = "speicherDatum",
            qualifiedByName = "localDateToOffSetDate"
    )
    @Mapping(
            source = "mikroDatPlan",
            target = "mikroDatPlan",
            qualifiedByName = "localDateToOffSetDate"
    )
    @Mapping(
            source = "mikroDat",
            target = "mikroDat",
            qualifiedByName = "localDateToOffSetDate"
    )
    ArchivResponseDTO toDTO(Archiv archiv);

    @Mapping(source = "projnr", target = "projekt.projnr")
    @Mapping(
            source = "speicherDatum",
            target = "speicherDatum",
            qualifiedByName = "offSetDateTimeToLocalDate"
    )
    @Mapping(
            source = "mikroDatPlan",
            target = "mikroDatPlan",
            qualifiedByName = "offSetDateTimeToLocalDate"
    )
    @Mapping(
            source = "mikroDat",
            target = "mikroDat",
            qualifiedByName = "offSetDateTimeToLocalDate"
    )
    Archiv toEntity(ArchivCreateDTO archivCreateDTO);

    @Mapping(target = "projekt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(
            source = "speicherDatum",
            target = "speicherDatum",
            qualifiedByName = "offSetDateTimeToLocalDate"
    )
    @Mapping(
            source = "mikroDatPlan",
            target = "mikroDatPlan",
            qualifiedByName = "offSetDateTimeToLocalDate"
    )
    @Mapping(
            source = "mikroDat",
            target = "mikroDat",
            qualifiedByName = "offSetDateTimeToLocalDate"
    )
    Archiv toEntity(ArchivUpdateDTO archivUpdateDTO);

}
