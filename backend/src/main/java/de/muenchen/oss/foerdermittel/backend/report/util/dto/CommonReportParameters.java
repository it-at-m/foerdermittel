package de.muenchen.oss.foerdermittel.backend.report.util.dto;

/**
 * Contains common search parameters shared by multiple reports.
 */
public record CommonReportParameters(
        String fb,
        String ua,
        String kurz,
        String sgt,
        String bpg,
        String sbl,
        String bez,
        String pstrasse,
        String pname,
        Enum<?> krisofp,
        String offen) {
}
