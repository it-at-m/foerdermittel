package de.muenchen.oss.foerdermittel.backend.report.auswertungantrag.formcontext;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.Antrag;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto.SiedlungsgebietFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameStadtbezirkslisteFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReportAuswertungAntraegeFormContext(
        @NotNull List<ListennameStadtbezirkslisteFormContextDTO> sbls,
        @NotNull List<StadtbezirkFormContextDTO> bezs,
        @NotNull List<FoerderbereichFormContextDTO> fbs,
        @NotNull List<UnterabschnittFormContextDTO> uas,
        @NotNull List<KurzbezeichnungFormContextDTO> kurzs,
        @NotNull List<Krisofp> krisofps,
        @NotNull List<Antrag> antraege,
        @NotNull List<SiedlungsgebietFormContextDTO> sgts,
        @NotNull List<BauprogrammFormContextDTO> bpgs) {

    public ReportAuswertungAntraegeFormContext {
        sbls = List.copyOf(sbls);
        bezs = List.copyOf(bezs);
        fbs = List.copyOf(fbs);
        uas = List.copyOf(uas);
        kurzs = List.copyOf(kurzs);
        krisofps = List.copyOf(krisofps);
        antraege = List.copyOf(antraege);
        sgts = List.copyOf(sgts);
        bpgs = List.copyOf(bpgs);

    }
}
