package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProjektMapper {

    @Mapping(source = "foerderbereich.fb", target = "foerderbereich")
    ProjektResponseDTO toDTO(Projekt projekt);

    @Mapping(source = "foerderbereich.fb", target = "foerderbereich")
    ProjektFormContextDTO toFormContext(Projekt projekt);

    List<ProjektFormContextDTO> toFormContext(List<Projekt> projektList);

    @Mapping(source = "projnr", target = "projnr")
    @Mapping(source = "jahr", target = "jahr")
    @Mapping(source = "stadtbezirk.stadtbezirk", target = "bez")
    @Mapping(source = "foerderbereich.fb", target = "fb")
    @Mapping(source = "unterabschnitt.ua", target = "ua")
    @Mapping(source = "kurzbezeichnung.kurzbez", target = "kurz")
    @Mapping(source = "pstrasse", target = "pstrasse")
    @Mapping(source = "pname", target = "pname")
    @Mapping(source = "krisofp", target = "krisofp")
    @Mapping(source = "siedlungsgebiet.bezeichnung", target = "sgt")
    @Mapping(source = "bauprogramm.bauprogramm", target = "bpg")
    ReportAuswertungProjektFormContextDTO toReportFormContext(Projekt projekt);

    List<ReportAuswertungProjektFormContextDTO> toReportFormContext(
            List<Projekt> projekte);
}