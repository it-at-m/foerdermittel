package de.muenchen.oss.foerdermittel.backend.benutzerhinweis.dto;

import jakarta.validation.constraints.NotNull;

public record BenutzerhinweisUpdateDTO(@NotNull String funktionsbeschreibung, @NotNull String bedienung, @NotNull String pruefungVorgaben) {
}
