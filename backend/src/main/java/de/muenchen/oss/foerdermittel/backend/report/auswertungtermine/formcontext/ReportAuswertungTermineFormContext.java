package de.muenchen.oss.foerdermittel.backend.report.auswertungtermine.formcontext;

import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungFormContextDTO;

import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameStadtbezirkslisteFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReportAuswertungTermineFormContext(
        @NotNull List<ListennameStadtbezirkslisteFormContextDTO> sbls,
        @NotNull List<StadtbezirkFormContextDTO> bezs,
        @NotNull List<FoerderbereichFormContextDTO> fbs,
        @NotNull List<UnterabschnittFormContextDTO> uas,
        @NotNull List<KurzbezeichnungFormContextDTO> kurzs) {

    public ReportAuswertungTermineFormContext {
        sbls = List.copyOf(sbls);
        bezs = List.copyOf(bezs);
        fbs = List.copyOf(fbs);
        uas = List.copyOf(uas);
        kurzs = List.copyOf(kurzs);

    }
}
