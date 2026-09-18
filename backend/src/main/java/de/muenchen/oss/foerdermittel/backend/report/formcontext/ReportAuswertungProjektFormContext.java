package de.muenchen.oss.foerdermittel.backend.report.formcontext;

import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportAuswertungProjektFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReportAuswertungProjektFormContext(@NotNull List<ReportAuswertungProjektFormContextDTO> projekte) {
    public ReportAuswertungProjektFormContext {
        projekte = List.copyOf(projekte);
    }
}
