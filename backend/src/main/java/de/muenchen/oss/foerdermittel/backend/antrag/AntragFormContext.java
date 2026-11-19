package de.muenchen.oss.foerdermittel.backend.antrag;

import de.muenchen.oss.foerdermittel.backend.antrag.dto.AntragProjektFormContextDTO;
import jakarta.validation.constraints.NotNull;
import java.util.List;

/// FormContext for [Antrag].
///
/// @param projekte

public record AntragFormContext(
        @NotNull List<AntragProjektFormContextDTO> projekte) {
    public AntragFormContext {
        projekte = List.copyOf(projekte);
    }
}
