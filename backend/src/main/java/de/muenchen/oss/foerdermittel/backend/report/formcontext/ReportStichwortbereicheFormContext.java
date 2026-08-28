package de.muenchen.oss.foerdermittel.backend.report.formcontext;

import de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto.StichwortbereichFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReportStichwortbereicheFormContext(@NotNull List<StichwortbereichFormContextDTO> bereiche) {
    public ReportStichwortbereicheFormContext {
        bereiche = List.copyOf(bereiche);
    }
}
