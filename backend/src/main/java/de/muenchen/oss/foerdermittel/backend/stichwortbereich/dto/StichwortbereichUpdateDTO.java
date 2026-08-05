package de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StichwortbereichUpdateDTO(@NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
