package de.muenchen.oss.foerdermittel.backend.unterabschnitt;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UnterabschnittFormContext(@NotNull List<String> uas, @NotNull List<String> hasHas) {
    public UnterabschnittFormContext {
        uas = List.copyOf(uas);
        hasHas = List.copyOf(hasHas);
    }
}
