package de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SiedlungsgebietUpdateDTO(@NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
