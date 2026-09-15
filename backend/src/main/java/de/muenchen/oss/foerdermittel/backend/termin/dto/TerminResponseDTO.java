package de.muenchen.oss.foerdermittel.backend.termin.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TerminResponseDTO(
        @NotNull String id,
        @NotNull LocalDate termin,
        Boolean ueberwachung,
        String zustaendig,
        String telefon,
        String notizen,
        @NotNull String projnr,
        @NotNull String pname,
        @NotNull String pstrasse,
        @NotNull BigDecimal fob_fb,
        @NotNull BigDecimal bez_stadtbezirk) {
}
