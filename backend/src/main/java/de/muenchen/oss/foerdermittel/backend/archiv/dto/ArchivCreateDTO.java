package de.muenchen.oss.foerdermittel.backend.archiv.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ArchivCreateDTO(LocalDate speicherDatum,
                              @NotNull Boolean speicherAkt,
                              @NotNull Boolean speicherRechnungen,
                              LocalDate mikroDatPlan,
                              LocalDate mikroDat,
                              String notizen) {
}
