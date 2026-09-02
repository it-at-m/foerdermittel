package de.muenchen.oss.foerdermittel.backend.unterabschnitt;

import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto.HauptabschnittFormContextDTO;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UnterabschnittFormContext(
        @NotNull List<String> uas,
        @NotNull List<HauptabschnittFormContextDTO> hasHas) {

    public UnterabschnittFormContext {
        uas = List.copyOf(uas);
        hasHas = List.copyOf(hasHas);
    }
}
