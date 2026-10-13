package de.muenchen.oss.foerdermittel.backend.istkosten.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record IstkostenUpdateDTO(
        @NotNull @Min(0) @Digits(integer = 12, fraction = 0) BigDecimal istkosten) {
}
