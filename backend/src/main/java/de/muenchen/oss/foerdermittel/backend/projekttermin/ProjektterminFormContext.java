package de.muenchen.oss.foerdermittel.backend.projekttermin;

import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProjektterminFormContext(
        @NotNull List<Long> terminID,
        @NotNull List<ProjektFormContextDTO> projekte
) {
    public ProjektterminFormContext {
        terminID = List.copyOf(terminID);
        projekte = List.copyOf(projekte);
    }
}
