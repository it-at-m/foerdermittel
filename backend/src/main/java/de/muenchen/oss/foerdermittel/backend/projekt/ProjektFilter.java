package de.muenchen.oss.foerdermittel.backend.projekt;

import java.math.BigDecimal;

public record ProjektFilter(
        String projnr,
        BigDecimal fobfb,
        String kurzbez,
        String uasUa,
        String pname,
        String pstrasse) {
}
