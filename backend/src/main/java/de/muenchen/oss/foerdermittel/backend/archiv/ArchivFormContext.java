package de.muenchen.oss.foerdermittel.backend.archiv;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ArchivFormContext(@NotNull List<Long> archivId) {
    public ArchivFormContext {
        archivId = List.copyOf(archivId);
    }
}
