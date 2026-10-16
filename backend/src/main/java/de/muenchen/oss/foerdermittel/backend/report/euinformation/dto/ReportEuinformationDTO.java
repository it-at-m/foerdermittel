package de.muenchen.oss.foerdermittel.backend.report.euinformation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ReportEuinformationDTO(
        @Pattern(regexp = "^(|19[7-9]\\d|20\\d{2}|2100)$") String jahrVon,
        @Pattern(regexp = "^(|19[7-9]\\d|20\\d{2}|2100)$") String jahrBis,
        @Size(max = 40) String inhalt,
        String publikation,
        @Pattern(regexp = "^(?:[1-9]|1[0-7]|20)$") String referatId,
        @NotNull ReportEuinformationSort sort) {
}
