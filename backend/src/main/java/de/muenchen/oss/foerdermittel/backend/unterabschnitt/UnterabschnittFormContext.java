package de.muenchen.oss.foerdermittel.backend.unterabschnitt;

import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.dto.HauptabschnittResponseDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UnterabschnittFormContext(@NotNull List<String> uas, @NotNull List<HauptabschnittResponseDTO> hasHas) {
    public UnterabschnittFormContext {
        uas = List.copyOf(uas);
        hasHas = List.copyOf(hasHas);
    }
}
