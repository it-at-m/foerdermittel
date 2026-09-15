package de.muenchen.oss.foerdermittel.backend.report.formcontext;

import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportProjektuebersichtFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReportProjektuebersichtFormContext(@NotNull List<ReportProjektuebersichtFormContextDTO> projekte) {
    public ReportProjektuebersichtFormContext {
        projekte = List.copyOf(projekte);
    }
}
