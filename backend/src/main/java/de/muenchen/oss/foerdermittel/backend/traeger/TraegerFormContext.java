package de.muenchen.oss.foerdermittel.backend.traeger;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record TraegerFormContext(@NotNull List<BigDecimal> traeger) {
    public TraegerFormContext {
        traeger = List.copyOf(traeger);
    }
}
