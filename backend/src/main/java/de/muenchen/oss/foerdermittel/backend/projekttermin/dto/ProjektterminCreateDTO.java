package de.muenchen.oss.foerdermittel.backend.projekttermin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record ProjektterminCreateDTO(
        @NotNull OffsetDateTime termin,
        Boolean ueberwachung,
        String zustaendig,
        String telefon,
        String notizen,
        @NotBlank String projnr) {
}
