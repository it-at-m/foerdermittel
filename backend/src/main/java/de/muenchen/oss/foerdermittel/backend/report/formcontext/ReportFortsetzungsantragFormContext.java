package de.muenchen.oss.foerdermittel.backend.report.formcontext;

import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.hhplan.dto.HhplanFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameStadtbezirkslisteFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReportFortsetzungsantragFormContext(@NotNull List<ListennameStadtbezirkslisteFormContextDTO> sbls,
                                                  @NotNull List<StadtbezirkFormContextDTO> bezs) {
    public ReportFortsetzungsantragFormContext {
        sbls = List.copyOf(sbls);
        bezs = List.copyOf(bezs);
    }
}
