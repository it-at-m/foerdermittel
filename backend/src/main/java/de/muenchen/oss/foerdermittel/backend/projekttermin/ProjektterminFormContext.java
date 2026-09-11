package de.muenchen.oss.foerdermittel.backend.projekttermin;

import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/// DTO for [de.muenchen.oss.foerdermittel.backend.projekttermin.Projekttermin] to be used in other
/// FormContexts other than its own.
///
/// @param terminID
/// @param projekte

public record ProjektterminFormContext(
        @NotNull List<Long> terminID,
        @NotNull List<ProjektFormContextDTO> projekte) {
    public ProjektterminFormContext {
        terminID = List.copyOf(terminID);
        projekte = List.copyOf(projekte);
    }
}
