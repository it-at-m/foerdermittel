package de.muenchen.oss.foerdermittel.backend.foerderbereich;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record FoerderbereichFormContext(@NotNull List<BigDecimal> fbs) {
    public FoerderbereichFormContext {
        fbs = List.copyOf(fbs);
    }
}
