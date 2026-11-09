package de.muenchen.oss.foerdermittel.backend.report.euinformation.formcontext;

import de.muenchen.oss.foerdermittel.backend.publikation.dto.PublikationFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReportEuinformationFormContext(@NotNull List<PublikationFormContextDTO> publikationen) {
    public ReportEuinformationFormContext {
        publikationen = List.copyOf(publikationen);
    }
}
