package de.muenchen.oss.foerdermittel.backend.benutzerhinweis.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BenutzerhinweisCreateDTO(@NotNull @Size(min = 1, max = 30) String viewId, @NotNull String funktionsbeschreibung, @NotNull String bedienung,
        @NotNull String pruefungVorgaben) {
}
