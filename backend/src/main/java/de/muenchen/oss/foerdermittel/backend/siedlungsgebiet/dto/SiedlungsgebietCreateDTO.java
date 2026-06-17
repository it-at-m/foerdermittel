package de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SiedlungsgebietCreateDTO(@NotNull @Min(1) @Max(99) Integer siedlungsgebiet, @NotNull @Size(max = 200) String bezeichnung) {
}
