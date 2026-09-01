package de.muenchen.oss.foerdermittel.backend.projekttermin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ProjektterminResponseDTO(
        @NotNull Long id,
        @NotBlank OffsetDateTime termin,
        Boolean ueberwachung,
        String zustaendig,
        String telefon,
        String notizen,
        @NotNull String projnr,
        @NotNull String pname,
        @NotNull String pstrasse,
        @NotNull BigDecimal fob_fb,
        @NotNull BigDecimal bez_stadtbezirk
        ) {
}
