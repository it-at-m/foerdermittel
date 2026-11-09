package de.muenchen.oss.foerdermittel.backend.antrag.dto;

import de.muenchen.oss.foerdermittel.backend.antrag.Antragstyp;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record AntragResponseDTO(
        @NotNull String id,
        @NotNull String projnr,
        @NotNull String pname,
        @NotNull String pstrasse,
        @NotNull BigDecimal fob_fb,
        @NotNull BigDecimal bez_stadtbezirk,
        @NotNull Antragstyp antragstyp,
        LocalDate antragsdatum,
        @NotNull BigDecimal geskosten,
        @NotNull BigDecimal zwfkosten,
        @NotNull Boolean vorzbeg,
        LocalDate vbdatum,
        LocalDate unbeddat,
        LocalDate unbedja,
        LocalDate unbedbis,
        @NotNull BigDecimal asuZ,
        @NotNull BigDecimal asuD,
        @NotNull BigDecimal asuK,
        @NotNull BigDecimal bvorSuZ,
        @NotNull BigDecimal bvorSuD,
        @NotNull BigDecimal bvorSuK,
        String notizen,
        @NotNull LocalDateTime anlagedatum,
        @NotNull String anlagevon,
        LocalDateTime aenderungsdatum,
        String aenderungvon) {
}
