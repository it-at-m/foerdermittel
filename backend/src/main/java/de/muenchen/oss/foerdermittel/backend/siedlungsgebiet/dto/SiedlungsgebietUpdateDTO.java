package de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SiedlungsgebietUpdateDTO(@NotBlank @Size(min = 1, max = 200) String bezeichnung) {
}
