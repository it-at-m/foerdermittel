package de.muenchen.oss.foerdermittel.backend.stichwortbereich.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StichwortbereichUpdateDTO(@NotBlank @Size(min = 1, max = 200) String bezeichnung) {
}
