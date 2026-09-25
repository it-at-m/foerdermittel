package de.muenchen.oss.foerdermittel.backend.report.dto;

import de.muenchen.oss.foerdermittel.backend.report.ReportFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.math.BigInteger;

public record ReportHaushalt1DTO(
        @NotBlank @Pattern(regexp = "\\d{4}") String haushaltsjahr,
        String fb,
        String fipo,
        String sbl,
        String bez,
        String hh,
        ReportHaushalt1Sort sort,
        ReportFormat type) {
}
