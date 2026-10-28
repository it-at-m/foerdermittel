package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.dao.AuswertungProjektDAO;
import de.muenchen.oss.foerdermittel.backend.projekt.dao.BasicProjektDAO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjektMapper {

    @Mapping(source = "fobFb.fb", target = "fobFb")
    @Mapping(source = "kurKurzbez.kurzbez", target = "kurKurzbez")
    @Mapping(source = "uasUa.ua", target = "uasUa")
    @Mapping(source = "stadtbezirk.stadtbezirk", target = "stadtbezirk")
    ProjektResponseDTO toDTO(Projekt projekt);

    @Mapping(source = "fobFb.fb", target = "fobFb")
    @Mapping(source = "stadtbezirk.stadtbezirk", target = "stadtbezirk")
    ProjektFormContextDTO toFormContext(Projekt projekt);

    List<ProjektFormContextDTO> toFormContext(List<Projekt> projektList);

    List<ReportProjektuebersichtFormContextDTO> toReportProjektuebersichtFormContext(
            List<BasicProjektDAO> projekte);

    List<ReportAuswertungProjektFormContextDTO> toReportAuswertungProjektFormContext(
            List<AuswertungProjektDAO> projekte);
}
