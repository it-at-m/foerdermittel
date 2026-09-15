package de.muenchen.oss.foerdermittel.backend.termin;

import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/// DTO for [Termin] to be used in other
/// FormContexts other than its own.
///
/// @param terminID
/// @param projekte

public record TerminFormContext(
        @NotNull List<Long> terminID,
        @NotNull List<ProjektFormContextDTO> projekte) {
    public TerminFormContext {
        terminID = List.copyOf(terminID);
        projekte = List.copyOf(projekte);
    }
}
