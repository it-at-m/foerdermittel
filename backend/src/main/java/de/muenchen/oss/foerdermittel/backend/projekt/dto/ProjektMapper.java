package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.dao.AuswertungProjektDAO;
import de.muenchen.oss.foerdermittel.backend.projekt.dao.BasicProjektDAO;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjektMapper {

    @Mapping(source = "foerderbereich.fb", target = "foerderbereich")
    @Mapping(source = "stadtbezirk.stadtbezirk", target = "stadtbezirk")
    ProjektResponseDTO toDTO(Projekt projekt);

    Projekt toEntity(ProjektCreateDTO projektCreateDTO);

    @Mapping(target = "projnr", ignore = true)
    Projekt toEntity(ProjektUpdateDTO projektUpdateDTO);

    @Mapping(source = "foerderbereich.fb", target = "foerderbereich")
    @Mapping(source = "stadtbezirk.stadtbezirk", target = "stadtbezirk")
    ProjektFormContextDTO toFormContext(Projekt projekt);

    List<ProjektFormContextDTO> toFormContext(List<Projekt> projektList);

    List<ReportProjektuebersichtFormContextDTO> toReportProjektuebersichtFormContext(
            List<BasicProjektDAO> projekte);

    List<ReportAuswertungProjektFormContextDTO> toReportAuswertungProjektFormContext(
            List<AuswertungProjektDAO> projekte);
}
