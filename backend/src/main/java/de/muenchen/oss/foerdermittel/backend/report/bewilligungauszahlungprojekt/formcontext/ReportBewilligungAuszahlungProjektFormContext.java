package de.muenchen.oss.foerdermittel.backend.report.bewilligungauszahlungprojekt.formcontext;

import de.muenchen.oss.foerdermittel.backend.projekt.dto.ReportBewilligungAuszahlungProjektFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReportBewilligungAuszahlungProjektFormContext(@NotNull List<ReportBewilligungAuszahlungProjektFormContextDTO> projekte) {
    public ReportBewilligungAuszahlungProjektFormContext {
        projekte = List.copyOf(projekte);
    }
}
