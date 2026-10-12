package de.muenchen.oss.foerdermittel.backend.report.euinformation.dto;

import jakarta.validation.constraints.NotNull;

public record ReportEuinformationDTO(
        String jahrVon,
        String jahrBis,
        String inhalt,
        String publikation,
        String referatId,
        String referatBez,
        @NotNull ReportEuinformationSort sort){
}
