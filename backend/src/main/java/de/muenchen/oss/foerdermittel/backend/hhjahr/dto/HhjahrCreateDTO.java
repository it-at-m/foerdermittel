package de.muenchen.oss.foerdermittel.backend.hhjahr.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record HhjahrCreateDTO(
        @NotNull @Min(0) @Digits(integer = 4, fraction = 0) BigDecimal hhjahr,
        @NotNull @Size(max = 200) String hhbezeichnung,
        String notizen) {
}
