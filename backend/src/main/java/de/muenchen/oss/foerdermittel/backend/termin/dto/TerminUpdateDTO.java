package de.muenchen.oss.foerdermittel.backend.termin.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TerminUpdateDTO(
        @NotNull LocalDate termin,
        Boolean ueberwachung,
        @Size(min = 1, max = 30) String zustaendig,
        @Size(min = 1, max = 30) @Pattern(regexp = "\\d*") String telefon,
        @Size(min = 1, max = 40) String notizen) {
}
