package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.dao.AuswertungProjektDAO;
import de.muenchen.oss.foerdermittel.backend.projekt.dao.BasicProjektDAO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProjektMapper {

    @Mapping(source = "projnr", target = "id")
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
    void updateEntity(ProjektUpdateDTO source, @MappingTarget Projekt target);

    @Mapping(target = "projnr", source = "projnr")
    @Mapping(target = "foerderbereich", source = "foerderbereich.fb")
    @Mapping(target = "stadtbezirk", source = "stadtbezirk.stadtbezirk")
    @Mapping(target = "bauprogramm", source = "bauprogramm.bauprogramm")
    @Mapping(target = "krhname", source = "krankenhaus.krhname")
    @Mapping(target = "siedlungsgebiet", source = "siedlungsgebiet.siedlungsgebiet")
    @Mapping(target = "uasUa", source = "unterabschnitt.ua")
    @Mapping(target = "kurzbez", source = "kurzbezeichnung.kurzbez")
    ProjektFormContextDTO toFormContext(Projekt projekt);

    List<ProjektFormContextDTO> toFormContext(List<Projekt> projektList);

    List<ReportProjektuebersichtFormContextDTO> toReportProjektuebersichtFormContext(
            List<BasicProjektDAO> projekte);

    List<ReportAuswertungProjektFormContextDTO> toReportAuswertungProjektFormContext(
            List<AuswertungProjektDAO> projekte);

    List<ReportBewilligungAuszahlungProjektFormContextDTO> toReportBewilligungAuszahlungProjektFormContext(
            List<BasicProjektDAO> projekte);
}
