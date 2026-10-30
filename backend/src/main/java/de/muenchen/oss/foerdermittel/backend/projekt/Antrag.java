package de.muenchen.oss.foerdermittel.backend.projekt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Antrag {

    E("Erstantrag"),
    V("Schlussantrag (VN)"),
    A("Änderungsantrag"),
    F("Fortsetzungsantrag"),
    U("Unbedenklichkeitsantrag");

    private final String bezeichnung;

    public String getKurzform() {
        return name();
    }
}
