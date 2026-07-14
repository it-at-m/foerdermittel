package de.muenchen.oss.foerdermittel.backend.listenname.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ListennameCreateDTO(@NotNull @Size(min = 1, max = 3) @Pattern(regexp = "^[A-Z0-9]+$") String kurzbez,
                                  @NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
