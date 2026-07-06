package de.muenchen.oss.foerdermittel.backend.benutzerhinweis.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BenutzerhinweisUpdateDTO(@NotNull @Size(max = 4000) String funktionsbeschreibung, @NotNull @Size(max = 4000) String bedienung,
        @NotNull @Size(max = 4000) String pruefungVorgaben) {
}
