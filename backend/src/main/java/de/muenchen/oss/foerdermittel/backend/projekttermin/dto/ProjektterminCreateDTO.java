package de.muenchen.oss.foerdermittel.backend.projekttermin.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.OffsetDateTime;

public record ProjektterminCreateDTO(
        @NotBlank OffsetDateTime termin,
        Boolean ueberwachung,
         String zustaendig,
        String telefon,
        String notizen,
        @NotBlank String projnr
) {
}
