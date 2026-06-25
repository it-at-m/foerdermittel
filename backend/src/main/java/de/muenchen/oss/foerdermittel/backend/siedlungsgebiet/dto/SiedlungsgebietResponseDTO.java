package de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto;

import jakarta.validation.constraints.NotNull;

public record SiedlungsgebietResponseDTO(@NotNull String id, @NotNull Integer siedlungsgebiet, @NotNull String bezeichnung) {
}
