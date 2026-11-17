package de.muenchen.oss.foerdermittel.backend.hhplan;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record HhplanFormContext(@NotNull List<BigDecimal> hhp) {
    public HhplanFormContext {
        hhp = List.copyOf(hhp);
    }
}
