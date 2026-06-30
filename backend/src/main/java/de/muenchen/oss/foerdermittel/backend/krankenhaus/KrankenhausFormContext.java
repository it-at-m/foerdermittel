package de.muenchen.oss.foerdermittel.backend.krankenhaus;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record KrankenhausFormContext(@NotNull List<String> krhNamen) {
    public KrankenhausFormContext {
        krhNamen = List.copyOf(krhNamen);
    }
}