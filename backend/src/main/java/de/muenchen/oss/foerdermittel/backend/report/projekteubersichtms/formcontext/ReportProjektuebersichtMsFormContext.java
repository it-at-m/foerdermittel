package de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.formcontext;


import de.muenchen.oss.foerdermittel.backend.bauprogramm.dto.BauprogrammFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.dto.FoerderbereichFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.KurzbezeichnungFormContext;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto.KurzbezeichnungFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto.SiedlungsgebietFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.dto.StadtbezirkFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.StadtbezirkslisteFormContext;
import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.ListennameStadtbezirkslisteFormContextDTO;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReportProjektuebersichtMsFormContext(
        @NotNull List<ListennameStadtbezirkslisteFormContextDTO> stadtbezirkslisten,
        @NotNull List<StadtbezirkFormContextDTO> stadtbezirke,
        @NotNull List <FoerderbereichFormContextDTO> foerderbereiche,
        @NotNull List <UnterabschnittFormContextDTO> unterabschnitte,
        @NotNull List <KurzbezeichnungFormContextDTO> kurzbezeichnungen,
        @NotNull List <Krisofp> krisofps,
        @NotNull List <SiedlungsgebietFormContextDTO> siedlungsgebiete,
        @NotNull List <BauprogrammFormContextDTO> bauprogramme) {
        public ReportProjektuebersichtMsFormContext {
            stadtbezirkslisten = List.copyOf(stadtbezirkslisten);
            stadtbezirke = List.copyOf(stadtbezirke);
            foerderbereiche = List.copyOf(foerderbereiche);
            unterabschnitte = List.copyOf(unterabschnitte);
            kurzbezeichnungen = List.copyOf(kurzbezeichnungen);
            krisofps = List.copyOf(krisofps);
            siedlungsgebiete = List.copyOf(siedlungsgebiete);
            bauprogramme = List.copyOf(bauprogramme);

        }
}
