package de.muenchen.oss.foerdermittel.backend.projekt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Krhzweck {

    J("Jahreskrankenhausprogramm"),
    S("Jahreskrankenhausprogramm"),
    R("Jahreskrankenhausprogramm");

    private final String bezeichnung;

    public String getKurzform() {
        return name();
    }

}
