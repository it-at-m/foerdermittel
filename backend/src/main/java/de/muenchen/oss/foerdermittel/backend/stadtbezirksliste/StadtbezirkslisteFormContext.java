package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record StadtbezirkslisteFormContext(@NotNull List<String> stadtbezirkslistenIds) {
    public StadtbezirkslisteFormContext {
        stadtbezirkslistenIds = List.copyOf(stadtbezirkslistenIds);
    }
}
