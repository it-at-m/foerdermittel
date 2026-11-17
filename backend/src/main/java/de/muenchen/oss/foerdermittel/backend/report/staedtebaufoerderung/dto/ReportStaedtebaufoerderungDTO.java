package de.muenchen.oss.foerdermittel.backend.report.staedtebaufoerderung.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ReportStaedtebaufoerderungDTO(
        @Pattern(regexp = "^(|19[7-9]\\d|20\\d{2}|2100)$") String jahrVon,
        @Pattern(regexp = "^(|19[7-9]\\d|20\\d{2}|2100)$") String jahrBis,
        @Size(max = 40) String pname,
        @Pattern(regexp = "^[0-9]?$") String traeger,
        String notizen) {
}
