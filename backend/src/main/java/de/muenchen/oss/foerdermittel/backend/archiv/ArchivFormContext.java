package de.muenchen.oss.foerdermittel.backend.archiv;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektResponseDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ArchivFormContext(
        @NotNull List<Long> archivId,
        @NotNull List<ProjektResponseDTO> projekte
) {
    public ArchivFormContext {
        archivId = List.copyOf(archivId);
        projekte = List.copyOf(projekte);
    }
}
