package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper
public interface ProjektMapper {
    List<ReportProjektuebersichtFormContextDTO> toReportFormContext(List<Projekt> projekte);
}
