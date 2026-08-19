package de.muenchen.oss.foerdermittel.backend.projekt;

import java.math.BigDecimal;

public record ProjektFilter(
        String projnr,
        BigDecimal fobFb,
        String kurKurzbez,
        String uasUa,
        String pname,
        String pstrasse
) {
}