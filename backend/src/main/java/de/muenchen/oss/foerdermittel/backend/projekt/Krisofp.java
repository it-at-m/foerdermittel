package de.muenchen.oss.foerdermittel.backend.projekt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Krisofp {

    X("1.+2. SIP - KriSoFö 2008-2014"),
    Y("3. SIP - Kinderbetreuung 2015-2019"),
    Z("4. SIP - Kinderbetreuung 2017-2020/2021"),
    W("5. SIP - Schulkindbetreuung Land (Hortförd. GS)"),
    V("Schulkindbetreuung - Bund (GaFöG)"),
    K("KJP II"),
    F("FAGplus15"),
    S("Startchancen-Programm (SCP)");

    private final String bezeichnung;

    public String getKurzform() {
        return name();
    }
}
