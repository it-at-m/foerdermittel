package de.muenchen.oss.foerdermittel.backend.report.annahmeanordnungsap.formcontext;

import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportAnnahmeAnordnungSapFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReportAnnahmeAnordnungSapFormContext(@NotNull List<ReportAnnahmeAnordnungSapFormContextDTO> projekte) {
    public ReportAnnahmeAnordnungSapFormContext {
        projekte = List.copyOf(projekte);
    }
}
