package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StadtbezirkslisteUpdateDTO(@NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
