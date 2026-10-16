package de.muenchen.oss.foerdermittel.backend.projekt;

import java.math.BigDecimal;

public record ProjektFilter(
        String projnr,
        BigDecimal foerderbereich,
        String kurKurzbez,
        String uasUa,
        String pname,
        String pstrasse) {
}
