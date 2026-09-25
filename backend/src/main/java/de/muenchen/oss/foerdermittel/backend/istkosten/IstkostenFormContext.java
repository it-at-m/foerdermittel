package de.muenchen.oss.foerdermittel.backend.istkosten;

import de.muenchen.oss.foerdermittel.backend.projekt.dto.ProjektFormContextDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record IstkostenFormContext(@NotNull List<IstkostenPrimaryKey> istkosten,
        @NotNull List<ProjektFormContextDTO> projekte) {
    public IstkostenFormContext {
        istkosten = List.copyOf(istkosten);
        projekte = List.copyOf(projekte);
    }
}
