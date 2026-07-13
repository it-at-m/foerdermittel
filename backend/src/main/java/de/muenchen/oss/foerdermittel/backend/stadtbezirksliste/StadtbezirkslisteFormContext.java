package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record StadtbezirkslisteFormContext(@NotNull List<String> stadtbezirkslisten) {
    public StadtbezirkslisteFormContext {
        stadtbezirkslisten = List.copyOf(stadtbezirkslisten);
    }
}
