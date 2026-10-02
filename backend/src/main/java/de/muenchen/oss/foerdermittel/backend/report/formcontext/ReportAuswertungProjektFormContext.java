package de.muenchen.oss.foerdermittel.backend.report.formcontext;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportAuswertungProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto.SiedlungsgebietFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameStadtbezirkslisteFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReportAuswertungProjektFormContext(
        @NotNull List<ReportAuswertungProjektFormContextDTO> projekte,
        @NotNull List<FoerderbereichFormContextDTO> fbs,
        @NotNull List<ListennameStadtbezirkslisteFormContextDTO> sbls,
        @NotNull List<StadtbezirkFormContextDTO> bezs,
        @NotNull List<UnterabschnittFormContextDTO> uas,
        @NotNull List<KurzbezeichnungFormContextDTO> kurzs,
        @NotNull List<BauprogrammFormContextDTO> bpgs,
        @NotNull List<SiedlungsgebietFormContextDTO> sgts,
        @NotNull List<Krisofp> krisofps
        ) {
    public ReportAuswertungProjektFormContext {
        projekte = List.copyOf(projekte);
        fbs = List.copyOf(fbs);
        sbls = List.copyOf(sbls);
        bezs = List.copyOf(bezs);
        uas = List.copyOf(uas);
        kurzs = List.copyOf(kurzs);
        bpgs = List.copyOf(bpgs);
        sgts = List.copyOf(sgts);
        krisofps = List.copyOf(krisofps);

    }
}
