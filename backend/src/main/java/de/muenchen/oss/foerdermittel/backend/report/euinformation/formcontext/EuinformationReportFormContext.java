package de.muenchen.oss.foerdermittel.backend.report.euinformation.formcontext;

import de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto.StichwortbereichFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record EuinformationReportFormContext(@NotNull List<StichwortbereichFormContextDTO> bereiche) {
    public EuinformationReportFormContext {
        bereiche = List.copyOf(bereiche);
    }
}
