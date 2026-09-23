package de.muenchen.oss.foerdermittel.backend.report.formcontext;

import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.StadtbezirkslisteFormContext;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameStadtbezirkslisteFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto.StichwortbereichFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReportStichworteFormContext(@NotNull List<StichwortbereichFormContextDTO> bereiche) {
    public ReportStichworteFormContext {
        bereiche = List.copyOf(bereiche);
    }
}
