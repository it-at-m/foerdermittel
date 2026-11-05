package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krhzweck;
import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProjektCreateDTO(
        // Required fields for project creation
        @NotNull @Size(min = 7, max = 7) @Pattern(regexp = "^[A-Z0-9]{7}$") String projnr,
        @NotNull @Max(99) Integer fobFb,
        @NotNull @Size(min = 1, max = 3) @Pattern(regexp = "^[A-Z0-9]+$") String kurKurzbez,
        @NotNull @Size(min = 1, max = 2) @Pattern(regexp = "^[A-Z0-9]+$") String uasUa,
        @NotNull @Min(0) @Max(99) Integer jahr,
        @NotNull @Min(0) @Max(9) Integer lfdnr1,
        @NotNull @Min(0) @Max(99) Integer lfdnr2,

        // Optional general information
        @Max(99) Integer bezStadtbezirk,
        @Size(max = 1) @Pattern(regexp = "^[A-Z]*$") String krnKrhname,
        @Max(99) Integer sgtSiedlungsgebiet,
        @Size(max = 100) String pname,
        @Size(max = 100) String pstrasse,
        @Size(max = 50) String projart,
        Boolean refinanzierbar,
        Boolean kauf,
        @Size(max = 100) String notizen,
        @Max(99) Integer bpgBauprogramm,
        Krisofp krisofp,
        Krhzweck krhzweck,
        @Digits(integer = 10, fraction = 0) @Min(0) BigDecimal kripplatz,
        @Digits(integer = 10, fraction = 0) @Min(0) BigDecimal kigaplatz,
        @Digits(integer = 10, fraction = 0) @Min(0) BigDecimal hortplatz) {
}
