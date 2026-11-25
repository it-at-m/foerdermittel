package de.muenchen.oss.foerdermittel.backend.report.euinformation.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ReportEuinformationDTO(
        @Min(1970) @Max(2100) @Digits(integer = 4, fraction = 0) Integer jahrVon,
        @Min(1970) @Max(2100) @Digits(integer = 4, fraction = 0) Integer jahrBis,
        @Size(max = 40) String inhalt,
        @Pattern(regexp = "^[A-Z0-9]*$") String publikation,
        @Pattern(regexp = "^(?:[1-9]|1[0-7]|20)$") String referatId,
        @NotNull ReportEuinformationSort sort) {
}
