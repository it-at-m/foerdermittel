package de.muenchen.oss.foerdermittel.backend.archiv.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ArchivCreateDTO(@NotNull LocalDate speicherDatum,
        @NotNull Boolean speicherAkt,
        @NotNull Boolean speicherRechnungen,
        @NotNull LocalDate mikroDatPlan,
        @NotNull LocalDate mikroDat,
        @NotNull String notizen,
        @NotNull String projnr) {
}
