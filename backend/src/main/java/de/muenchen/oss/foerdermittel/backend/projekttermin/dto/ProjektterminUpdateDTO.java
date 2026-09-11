package de.muenchen.oss.foerdermittel.backend.projekttermin.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

public record ProjektterminUpdateDTO(
        @NotNull OffsetDateTime termin,
        Boolean ueberwachung,
        @Size(min = 1, max = 30) String zustaendig,
        @Size(min = 1, max = 30) String telefon,
        String notizen) {
}
