package de.muenchen.oss.foerdermittel.backend.report.auswertungabrufe.formcontext;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportAuswertungAntragFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.referat.dto.ReferatFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto.SiedlungsgebietFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameStadtbezirkslisteFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReportAuswertungAbrufeFormContext(
        @NotNull List<ListennameStadtbezirkslisteFormContextDTO> sbls,
        @NotNull List<StadtbezirkFormContextDTO> bezs,
        @NotNull List<FoerderbereichFormContextDTO> fbs,
        @NotNull List<UnterabschnittFormContextDTO> uas,
        @NotNull List<KurzbezeichnungFormContextDTO> kurzs,
        @NotNull List<ReportAuswertungAntragFormContextDTO> projs,
        @NotNull List<ReferatFormContextDTO> refs,
        @NotNull List<KrankenhausFormContextDTO> krhs,
        @NotNull List<Krisofp> krisofps,
        @NotNull List<SiedlungsgebietFormContextDTO> sgts,
        @NotNull List<BauprogrammFormContextDTO> bpgs) {

    public ReportAuswertungAbrufeFormContext {
        sbls = List.copyOf(sbls);
        bezs = List.copyOf(bezs);
        fbs = List.copyOf(fbs);
        uas = List.copyOf(uas);
        kurzs = List.copyOf(kurzs);
        projs = List.copyOf(projs);
        refs = List.copyOf(refs);
        krhs = List.copyOf(krhs);
        krisofps = List.copyOf(krisofps);
        sgts = List.copyOf(sgts);
        bpgs = List.copyOf(bpgs);

    }
}
