package de.muenchen.oss.foerdermittel.backend.report.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ReportStaedtebaufoerderungDTO(
        @Pattern(regexp = "^(|19[7-9]\\d|20\\d{2}|2100)$") String jahrVon,
        @Pattern(regexp = "^(|19[7-9]\\d|20\\d{2}|2100)$") String jahrBis,
        @Size(max = 40) String pname,
        String traeger,
        String notizen
) {
}
