package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigInteger;

public record ProjektCreateDTO(
        @NotNull @Size(min = 7, max = 7) @Pattern(regexp = "^[A-Z0-9]{7}$") String projnr,
        @NotNull @Min(0) @Max(99) Integer fobFb,
        @NotNull @Size(min = 1, max = 3) @Pattern(regexp = "^[A-Z0-9]+$") String kurKurzbez,
        @NotNull @Size(min = 1, max = 2) @Pattern(regexp = "^[A-Z0-9]+$") String uasUa,
        @NotNull @Min(0) @Max(99) Integer jahr,
        @NotNull @Min(0) @Max(9) Integer lfdnr1,
        @NotNull @Min(0) @Max(99) Integer lfdnr2,
        @Size(max = 100)  String pname,
        @Size(max = 100)  String pstrasse) {
}
