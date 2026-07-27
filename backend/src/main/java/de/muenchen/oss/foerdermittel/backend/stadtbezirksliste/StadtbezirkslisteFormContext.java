package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record StadtbezirkslisteFormContext(@NotNull List<String> stadtbezirksliste) {
    public StadtbezirkslisteFormContext {
        stadtbezirksliste  = List.copyOf(stadtbezirksliste);
    }
}
