package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.dao.BasicProjektDAO;
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

    List<ReportProjektuebersichtFormContextDTO> toReportFormContext(List<BasicProjektDAO> projekte);
}
