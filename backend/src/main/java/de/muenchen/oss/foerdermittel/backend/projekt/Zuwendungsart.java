package de.muenchen.oss.foerdermittel.backend.projekt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Zuwendungsart {

    G("Gesamtzuwendung"),
    R("Restzuwendung"),
    T("Teilzuwendung"),
    V("Mittelverfall");

    private final String bezeichnung;

    public String getKurzform() {
        return name();
    }
}
