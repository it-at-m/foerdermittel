package de.muenchen.oss.foerdermittel.backend.archiv.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ArchivResponseDTO(@NotNull Long id, @NotNull LocalDate speicherDatum,
                                @NotNull Boolean speicherAkt,
                                @NotNull Boolean speicherRechnungen,
                                @NotNull LocalDate mikroDatPlan,
                                @NotNull LocalDate mikroDat,
                                @NotNull String notizen,
                                @NotNull String projnr,
                                @NotNull String pname,
                                @NotNull String pstrasse,
                                @NotNull BigDecimal fob_fb) {
}
