package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record StadtbezirkslisteResponseDTO(@NotNull String id, @NotNull String kurzbez, @NotNull String bezeichnung, @NotNull
                                           List<StadtbezirkslisteAssignmentResponseDTO> assignedStadtbezirke) {
}
