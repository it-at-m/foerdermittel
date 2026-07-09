package de.muenchen.oss.foerdermittel.backend.stadtbezirk;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record StadtbezirkFormContext(@NotNull List<BigDecimal> stadtbezirke) {
    public StadtbezirkFormContext {
        stadtbezirke = List.copyOf(stadtbezirke);
    }
}
