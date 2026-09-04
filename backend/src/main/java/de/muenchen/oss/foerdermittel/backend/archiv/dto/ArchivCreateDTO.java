package de.muenchen.oss.foerdermittel.backend.archiv.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record ArchivCreateDTO(
        OffsetDateTime speicherDatum,
        @NotNull Boolean speicherAkt,
        @NotNull Boolean speicherRechnungen,
        OffsetDateTime mikroDatPlan,
        OffsetDateTime mikroDat,
        String notizen,
        @NotBlank String projnr) {
}
