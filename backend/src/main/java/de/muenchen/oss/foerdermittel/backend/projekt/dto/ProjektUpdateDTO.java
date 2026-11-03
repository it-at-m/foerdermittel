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
        @Size(max = 100) String pname,
        @Size(max = 100) String pstrasse,
        @Max(99) Integer fobFb,
        @Max(99) Integer stadtbezirk,
        @Size(max = 1) @Pattern(regexp = "^[A-Z]*$") String krhname,
        @Size(max = 3) @Pattern(regexp = "^[A-Z0-9]+$") String kurKurzbez,
        @Max(99) Integer siedlungsgebiet,
        @Size(max = 50) String projart,
        Boolean refinanzierbar,
        Boolean kauf,
        @Digits(integer = 10, fraction = 0) @Min(0) BigDecimal kripplatz,
        @Digits(integer = 10, fraction = 0) @Min(0) BigDecimal kigaplatz,
        @Digits(integer = 10, fraction = 0) @Min(0) BigDecimal hortplatz,
        @Size(max = 100) String notizen,
        @Max(99) Integer bauprogramm,
        Krisofp krisofp,
        Krhzweck krhzweck) {
}
