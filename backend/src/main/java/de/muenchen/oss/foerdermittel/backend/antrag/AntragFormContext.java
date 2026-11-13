package de.muenchen.oss.foerdermittel.backend.antrag;

import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektFormContextDTO;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/// FormContext for [Antrag].
///
/// @param projekte

public record AntragFormContext(
        @NotNull List<ProjektFormContextDTO> projekte) {
    public AntragFormContext {
        projekte = List.copyOf(projekte);
    }
}
