package de.muenchen.oss.foerdermittel.backend.archiv;

import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ArchivFormContext(
        @NotNull List<Long> archivId,
        @NotNull List<ProjektFormContextDTO> projekte) {
    public ArchivFormContext {
        archivId = List.copyOf(archivId);
        projekte = List.copyOf(projekte);
    }
}
