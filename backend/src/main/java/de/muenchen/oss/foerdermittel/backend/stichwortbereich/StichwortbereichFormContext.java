package de.muenchen.oss.foerdermittel.backend.stichwortbereich;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record StichwortbereichFormContext(@NotNull List<String> bereiche) {
    public StichwortbereichFormContext {
        bereiche = List.copyOf(bereiche);
    }
}
