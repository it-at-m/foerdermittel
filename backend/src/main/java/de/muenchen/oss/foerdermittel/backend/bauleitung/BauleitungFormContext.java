package de.muenchen.oss.foerdermittel.backend.bauleitung;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BauleitungFormContext(@NotNull List<String> bauleitungen) {
    public BauleitungFormContext {
        bauleitungen = List.copyOf(bauleitungen);
    }
}
