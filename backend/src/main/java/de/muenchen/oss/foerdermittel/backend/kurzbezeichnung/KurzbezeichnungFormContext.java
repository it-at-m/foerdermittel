package de.muenchen.oss.foerdermittel.backend.kurzbezeichnung;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record KurzbezeichnungFormContext(@NotNull List<String> kurzbezeichnungen) {
    public KurzbezeichnungFormContext {
        kurzbezeichnungen = List.copyOf(kurzbezeichnungen);
    }
}
