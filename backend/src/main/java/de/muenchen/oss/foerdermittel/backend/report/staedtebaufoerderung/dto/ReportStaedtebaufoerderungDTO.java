package de.muenchen.oss.foerdermittel.backend.report.staedtebaufoerderung.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ReportStaedtebaufoerderungDTO(
        @Min(1970) @Max(2100) @Digits(integer = 4, fraction = 0) Integer jahrVon,
        @Min(1970) @Max(2100) @Digits(integer = 4, fraction = 0) Integer jahrBis,
        @Size(max = 40) String pname,
        @Pattern(regexp = "^[0-9]?$") String traeger,
        String notizen) {
}
