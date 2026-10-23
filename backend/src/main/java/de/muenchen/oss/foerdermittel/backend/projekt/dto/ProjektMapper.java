package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.dao.AuswertungProjektDAO;
import de.muenchen.oss.foerdermittel.backend.projekt.dao.BasicProjektDAO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjektMapper {

    @Mapping(source = "foerderbereich.fb", target = "fobFb")
    @Mapping(source = "unterabschnitt.ua", target = "uasUa")
    @Mapping(source = "kurzbezeichnung.kurzbez", target = "kurzbez")
    @Mapping(source = "stadtbezirk.stadtbezirk", target = "stadtbezirk")
    @Mapping(source = "krankenhaus.krhname", target = "krhname")
    @Mapping(source = "siedlungsgebiet.siedlungsgebiet", target = "siedlungsgebiet")
    @Mapping(source = "bauprogramm.bauprogramm", target = "bauprogramm")
    ProjektResponseDTO toDTO(Projekt projekt);

    @Mapping(target = "foerderbereich", ignore = true)
    @Mapping(target = "unterabschnitt", ignore = true)
    @Mapping(target = "kurzbezeichnung", ignore = true)
    @Mapping(target = "krankenhaus", ignore = true)
    @Mapping(target = "stadtbezirk", ignore = true)
    @Mapping(target = "siedlungsgebiet", ignore = true)
    @Mapping(target = "bauprogramm", ignore = true)
    Projekt toEntity(ProjektCreateDTO projektCreateDTO);

    @Mapping(target = "projnr", ignore = true)
    @Mapping(target = "jahr", ignore = true)
    @Mapping(target = "lfdnr1", ignore = true)
    @Mapping(target = "lfdnr2", ignore = true)
    @Mapping(target = "unterabschnitt", ignore = true)
    @Mapping(target = "foerderbereich", ignore = true)
    @Mapping(target = "kurzbezeichnung", ignore = true)
    @Mapping(target = "krankenhaus", ignore = true)
    @Mapping(target = "stadtbezirk", ignore = true)
    @Mapping(target = "siedlungsgebiet", ignore = true)
    @Mapping(target = "bauprogramm", ignore = true)
    Projekt toEntity(ProjektUpdateDTO projektUpdateDTO);

    @Mapping(source = "foerderbereich.fb", target = "foerderbereich")
    @Mapping(target = "stadtbezirk", ignore = true)
    ProjektFormContextDTO toFormContext(Projekt projekt);

    List<ProjektFormContextDTO> toFormContext(List<Projekt> projektList);

    List<ReportProjektuebersichtFormContextDTO> toReportProjektuebersichtFormContext(
            List<BasicProjektDAO> projekte);

    List<ReportAuswertungProjektFormContextDTO> toReportAuswertungProjektFormContext(
            List<AuswertungProjektDAO> projekte);
}
