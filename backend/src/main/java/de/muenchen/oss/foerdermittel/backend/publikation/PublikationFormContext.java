package de.muenchen.oss.foerdermittel.backend.publikation;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PublikationFormContext(@NotNull List<String> kurzformen) {
    public PublikationFormContext {
        kurzformen = List.copyOf(kurzformen);
    }
}
