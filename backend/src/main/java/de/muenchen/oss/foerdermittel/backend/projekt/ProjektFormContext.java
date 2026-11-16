package de.muenchen.oss.foerdermittel.backend.projekt;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.dto.KrankenhausFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto.SiedlungsgebietFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProjektFormContext(
        @NotNull List<ProjektFormContextDTO> projekte,
        @NotNull List<FoerderbereichFormContextDTO> fbs,
        @NotNull List<StadtbezirkFormContextDTO> bezs,
        @NotNull List<UnterabschnittFormContextDTO> uas,
        @NotNull List<KurzbezeichnungFormContextDTO> kurzs,
        @NotNull List<BauprogrammFormContextDTO> bpgs,
        @NotNull List<SiedlungsgebietFormContextDTO> sgts,
        @NotNull List<KrankenhausFormContextDTO> krhs,
        @NotNull List<Krisofp> krisofps,
        @NotNull List<Krhzweck> krhzwecks) {
    public ProjektFormContext {
        projekte = List.copyOf(projekte);
        fbs = List.copyOf(fbs);
        bezs = List.copyOf(bezs);
        uas = List.copyOf(uas);
        kurzs = List.copyOf(kurzs);
        bpgs = List.copyOf(bpgs);
        sgts = List.copyOf(sgts);
        krhs = List.copyOf(krhs);
        krisofps = List.copyOf(krisofps);
        krhzwecks = List.copyOf(krhzwecks);
    }
}
