package de.muenchen.oss.foerdermittel.backend.report.auswertungarchiv.formcontext;

import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportAuswertungArchivFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReportAuswertungArchivFormContext(
        @NotNull List<StadtbezirkFormContextDTO> bezs,
        @NotNull List<FoerderbereichFormContextDTO> fbs,
        @NotNull List<UnterabschnittFormContextDTO> uas,
        @NotNull List<KurzbezeichnungFormContextDTO> kurzs,
        @NotNull List<ReportAuswertungArchivFormContextDTO> projs,
        @NotNull List<Krisofp> krisofps) {

    public ReportAuswertungArchivFormContext {
        bezs = List.copyOf(bezs);
        fbs = List.copyOf(fbs);
        uas = List.copyOf(uas);
        kurzs = List.copyOf(kurzs);
        projs = List.copyOf(projs);
        krisofps = List.copyOf(krisofps);

    }
}
