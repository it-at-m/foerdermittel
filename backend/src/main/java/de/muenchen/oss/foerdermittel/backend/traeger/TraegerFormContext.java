package de.muenchen.oss.foerdermittel.backend.traeger;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record TraegerFormContext(@NotNull List<BigDecimal> kurzformen) {
    public TraegerFormContext {
        kurzformen = List.copyOf(kurzformen);
    }
}
