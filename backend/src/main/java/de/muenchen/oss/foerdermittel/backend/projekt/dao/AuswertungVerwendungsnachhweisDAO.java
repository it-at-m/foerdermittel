package de.muenchen.oss.foerdermittel.backend.projekt.dao;

import de.muenchen.oss.foerdermittel.backend.projekt.Krisofp;

import java.time.LocalDate;

public record AuswertungVerwendungsnachhweisDAO(
        LocalDate vndat,
        String pname,
        String pstrasse,
        Krisofp krisofp) {
}
