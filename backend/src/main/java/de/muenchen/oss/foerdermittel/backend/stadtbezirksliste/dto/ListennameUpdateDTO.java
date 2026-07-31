package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ListennameUpdateDTO(@NotNull @Size(min = 1, max = 200) String bezeichnung,   @NotNull
List<StadtbezirkslisteAssignmentResponseDTO> assignedStadtbezirke) {
}
