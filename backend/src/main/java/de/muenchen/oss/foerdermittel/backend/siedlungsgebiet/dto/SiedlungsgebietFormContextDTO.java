package de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto;

import jakarta.validation.constraints.NotNull;

/// @param siedlungsgebiet
/// @param bezeichnung

public record SiedlungsgebietFormContextDTO(
        @NotNull Integer siedlungsgebiet,
        @NotNull String bezeichnung) {
}
