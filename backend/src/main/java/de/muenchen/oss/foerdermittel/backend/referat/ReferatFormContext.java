package de.muenchen.oss.foerdermittel.backend.referat;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record ReferatFormContext(@NotNull List<BigDecimal> refNrs) {
    public ReferatFormContext {
        refNrs = List.copyOf(refNrs);
    }
}
