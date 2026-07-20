package de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto;

import jakarta.validation.constraints.NotNull;

public record KurzbezeichnungResponseDTO(@NotNull String id, @NotNull String kurzbez, @NotNull String bezeichnung) {
}
