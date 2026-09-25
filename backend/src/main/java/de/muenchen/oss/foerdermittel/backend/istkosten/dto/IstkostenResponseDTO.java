package de.muenchen.oss.foerdermittel.backend.istkosten.dto;

import de.muenchen.oss.foerdermittel.backend.istkosten.IstkostenPrimaryKey;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record IstkostenResponseDTO(
        @NotBlank String id,
        @NotBlank String projnr,
        @NotNull BigDecimal jahr,
        @NotNull BigDecimal monat,
        @NotNull BigDecimal istkosten,
        @NotNull String pname,
        @NotNull String pstrasse,
        @NotNull BigDecimal fob_fb
){
}
