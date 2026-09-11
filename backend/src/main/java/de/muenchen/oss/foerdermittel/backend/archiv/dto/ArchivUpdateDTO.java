package de.muenchen.oss.foerdermittel.backend.archiv.dto;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record ArchivUpdateDTO(
        OffsetDateTime speicherDatum,
        @NotNull Boolean speicherAkt,
        @NotNull Boolean speicherRechnungen,
        OffsetDateTime mikroDatPlan,
        OffsetDateTime mikroDat,
        String notizen) {
}
