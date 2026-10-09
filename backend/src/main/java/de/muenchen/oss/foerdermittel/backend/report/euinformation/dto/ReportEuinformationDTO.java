package de.muenchen.oss.foerdermittel.backend.report.euinformation.dto;

public record ReportEuinformationDTO(
        String jahrVon,
        String jahrBis,
        String inhalt,
        String publikation,
        String referatId,
        String referatBez) {
}
