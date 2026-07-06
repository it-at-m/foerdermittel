package de.muenchen.oss.foerdermittel.backend.benutzerhinweis.dto;

import jakarta.validation.constraints.NotNull;

public record BenutzerhinweisResponseDTO(@NotNull String id, @NotNull String viewId, @NotNull String funktionsbeschreibung, @NotNull String bedienung,
        @NotNull String pruefungVorgaben) {
}
