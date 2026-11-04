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
    @Mapping(source = "projnr", target = "projnr")
    @Mapping(source = "fobFb.fb", target = "fobFb")
    @Mapping(source = "uasUa.ua", target = "uasUa")
    @Mapping(source = "kurKurzbez.kurzbez", target = "kurKurzbez")
    @Mapping(source = "bezStadtbezirk.stadtbezirk", target = "bezStadtbezirk")
    @Mapping(source = "krnKrhname.krhname", target = "krnKrhname")
    @Mapping(source = "sgtSiedlungsgebiet.siedlungsgebiet", target = "sgtSiedlungsgebiet")
    @Mapping(source = "bpgBauprogramm.bauprogramm", target = "bpgBauprogramm")
    ProjektResponseDTO toDTO(Projekt projekt);

    @Mapping(target = "fobFb", ignore = true)
    @Mapping(target = "uasUa", ignore = true)
    @Mapping(target = "kurKurzbez", ignore = true)
    @Mapping(target = "krnKrhname", ignore = true)
    @Mapping(target = "bezStadtbezirk", ignore = true)
    @Mapping(target = "sgtSiedlungsgebiet", ignore = true)
    @Mapping(target = "bpgBauprogramm", ignore = true)
    Projekt toEntity(ProjektCreateDTO projektCreateDTO);

    @Mapping(target = "projnr", ignore = true)
    @Mapping(target = "jahr", ignore = true)
    @Mapping(target = "lfdnr1", ignore = true)
    @Mapping(target = "lfdnr2", ignore = true)
    @Mapping(target = "uasUa", ignore = true)
    @Mapping(target = "fobFb", ignore = true)
    @Mapping(target = "kurKurzbez", ignore = true)
    @Mapping(target = "krnKrhname", ignore = true)
    @Mapping(target = "bezStadtbezirk", ignore = true)
    @Mapping(target = "sgtSiedlungsgebiet", ignore = true)
    @Mapping(target = "bpgBauprogramm", ignore = true)
    void updateEntity(ProjektUpdateDTO source, @MappingTarget Projekt target);

    @Mapping(source = "projnr", target = "projnr")
    @Mapping(source = "fobFb.fb", target = "fobFb")
    @Mapping(source = "uasUa.ua", target = "uasUa")
    @Mapping(source = "kurKurzbez.kurzbez", target = "kurKurzbez")
    @Mapping(source = "bezStadtbezirk.stadtbezirk", target = "bezStadtbezirk")
    @Mapping(source = "krnKrhname.krhname", target = "krnKrhname")
    @Mapping(source = "sgtSiedlungsgebiet.siedlungsgebiet", target = "sgtSiedlungsgebiet")
    @Mapping(source = "bpgBauprogramm.bauprogramm", target = "bpgBauprogramm")
    ProjektFormContextDTO toFormContext(Projekt projekt);

    List<ProjektFormContextDTO> toFormContext(List<Projekt> projektList);

    List<ReportProjektuebersichtFormContextDTO> toReportProjektuebersichtFormContext(
            List<BasicProjektDAO> projekte);

    List<ReportAuswertungProjektFormContextDTO> toReportAuswertungProjektFormContext(
            List<AuswertungProjektDAO> projekte);

    List<ReportBewilligungAuszahlungProjektFormContextDTO> toReportBewilligungAuszahlungProjektFormContext(
            List<BasicProjektDAO> projekte);

    List<ReportAnnahmeAnordnungSapFormContextDTO> toReportAnnahmeAnordnungSapFormContext(
            List<BasicProjektDAO> projekte);
}
