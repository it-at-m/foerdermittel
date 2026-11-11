package de.muenchen.oss.foerdermittel.backend.projekt;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema (name = "ProjektFilter", description = "Filters for user searches")
public record ProjektFilter(
        String projnr,
        String fobFb,
        String kurz,
        String ua,
        String pname,
        String pstrasse) {
}
