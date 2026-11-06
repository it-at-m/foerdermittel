package de.muenchen.oss.foerdermittel.backend.hhjahr;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record HhjahrFormContext(@NotNull List<BigDecimal> hhj) {
    public HhjahrFormContext {
        hhj = List.copyOf(hhj);
    }
}
