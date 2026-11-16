package de.muenchen.oss.foerdermittel.backend.projekt;

/**
 * Filter object to use with Projekt.
 */
public record ProjektFilter(
        String projnr,
        String fobFb,
        String kurKurzbez,
        String uasUa,
        String pname,
        String pstrasse) {
}
