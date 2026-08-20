package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto;

import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record StadtbezirkslisteAssignmentResponseDTO(
        BigDecimal stadtbezirkId,
        String stadtbezirkBezeichnung,
        @Size(min = 1, max = 200) String bezeichnung) {
}
