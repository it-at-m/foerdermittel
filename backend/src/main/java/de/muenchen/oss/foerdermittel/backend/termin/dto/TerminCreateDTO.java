package de.muenchen.oss.foerdermittel.backend.termin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TerminCreateDTO(
        @NotNull LocalDate termin,
        Boolean ueberwachung,
        @Size(min = 1, max = 30) String zustaendig,
        @Size(min = 1, max = 30) String telefon,
        @Size(min = 1, max = 40) String notizen,
        @NotBlank String projnr) {
}
