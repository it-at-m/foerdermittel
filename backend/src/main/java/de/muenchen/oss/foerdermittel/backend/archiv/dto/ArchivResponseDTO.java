package de.muenchen.oss.foerdermittel.backend.archiv.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ArchivResponseDTO(
        @NotNull Long id,
        OffsetDateTime speicherDatum,
        @NotNull Boolean speicherAkt,
        @NotNull Boolean speicherRechnungen,
        OffsetDateTime mikroDatPlan,
        OffsetDateTime mikroDat,
        String notizen,
        @NotNull String projnr,
        @NotNull String pname,
        @NotNull String pstrasse,
        @NotNull BigDecimal fob_fb) {
}
