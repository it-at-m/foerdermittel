package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;

import java.time.LocalDate;

public record ReportAuswertungVerwendungsnachweisFormContextDTO(
        LocalDate vndat,
        String pstrasse,
        String pname,
        Krisofp krisofp) {
}
