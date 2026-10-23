package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krhzweck;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProjektUpdateDTO(
        @Size(min = 1, max = 100) String pname,
        @Size(min = 1, max = 100) String pstrasse,
        @Min(0) @Max(99) Integer fobFb,
        @Min(0) @Max(99) Integer stadtbezirk,
        @Size(min = 1, max = 1) @Pattern(regexp = "^[A-Z]+$") String krhname,
        @Size(min = 1, max = 3) @Pattern(regexp = "^[A-Z0-9]+$") String kurzbez,
        @Min(1) @Max(99) Integer siedlungsgebiet,
        @Size(min = 1, max = 50) String projart,
        Boolean refinanzierbar,
        Boolean kauf,
        @Digits(integer = 10, fraction = 0) BigDecimal kripplatz,
        @Digits(integer = 10, fraction = 0) BigDecimal kigaplatz,
        @Digits(integer = 10, fraction = 0) BigDecimal hortplatz,
        @Size(min = 1, max = 100) String notizen,
        @Min(1) @Max(99) Integer bauprogramm,
        Krisofp krisofp,
        Krhzweck krhzweck) {
}
