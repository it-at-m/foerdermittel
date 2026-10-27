package de.muenchen.oss.foerdermittel.backend.report.staedtebaufoerderung.formcontext;

import de.muenchen.oss.foerdermittel.backend.traeger.dto.TraegerFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReportStaedtebaufoerderungFormContext(@NotNull List<TraegerFormContextDTO> traeger) {
    public ReportStaedtebaufoerderungFormContext {
        traeger = List.copyOf(traeger);
    }
}
