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
        @NotNull List<Antrag> antraege,
        @NotNull List<BauprogrammFormContextDTO> bpgs,
        @NotNull List<FoerderbereichFormContextDTO> fbs,
        @NotNull List<Krisofp> krisofps,
        @NotNull List<KurzbezeichnungFormContextDTO> kurzs,
        @NotNull List<ListennameStadtbezirkslisteFormContextDTO> sbls,
        @NotNull List<SiedlungsgebietFormContextDTO> sgts,
        @NotNull List<StadtbezirkFormContextDTO> bezs,
        @NotNull List<UnterabschnittFormContextDTO> uas)
         {
    public ReportAuswertungAntraegeFormContext {
        antraege = List.copyOf(antraege);
        bpgs = List.copyOf(bpgs);
        fbs = List.copyOf(fbs);
        sbls = List.copyOf(sbls);
        bezs = List.copyOf(bezs);
        uas = List.copyOf(uas);
        krisofps = List.copyOf(krisofps);
        kurzs = List.copyOf(kurzs);
        sgts = List.copyOf(sgts);


    }
}
