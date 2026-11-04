package de.muenchen.oss.foerdermittel.backend.projekt;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProjektFormContext(@NotNull List<String> projekte) {
    public ProjektFormContext {
        projekte = List.copyOf(projekte);
    }
}
