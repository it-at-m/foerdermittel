package de.muenchen.oss.foerdermittel.backend.unterabschnitt;

import de.muenchen.oss.foerdermittel.backend.unterabschnitt.dto.UnterabschnittFormContextHauptabschnitt;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UnterabschnittFormContext(
        @NotNull List<String> uas,
        @NotNull List<UnterabschnittFormContextHauptabschnitt> hasHas) {

    public UnterabschnittFormContext {
        uas = List.copyOf(uas);
        hasHas = List.copyOf(hasHas);
    }
}
