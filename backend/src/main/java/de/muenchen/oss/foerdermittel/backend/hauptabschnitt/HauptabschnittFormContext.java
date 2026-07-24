package de.muenchen.oss.foerdermittel.backend.hauptabschnitt;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record HauptabschnittFormContext(@NotNull List<String> has) {
    public HauptabschnittFormContext {
        has = List.copyOf(has);
    }
}
