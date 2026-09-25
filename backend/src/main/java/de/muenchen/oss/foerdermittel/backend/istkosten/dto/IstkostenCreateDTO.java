package de.muenchen.oss.foerdermittel.backend.istkosten.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record IstkostenCreateDTO(
        @NotBlank String projnr,
        @NotNull @Min(1970) @Max(2100) BigDecimal jahr,
        @NotNull @Min(1) @Max(12) BigDecimal monat,
        @NotNull @Min(0) @Digits(integer = 12, fraction = 0) BigDecimal istkosten) {

}
