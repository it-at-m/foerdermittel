package de.muenchen.oss.foerdermittel.backend.antrag;

/// Mirrors the database enum `antraege_antragstyp`.
public enum Antragstyp {
    /// Änderungsantrag
    A,
    /// Erstantrag
    E,
    /// Fortsetzungsantrag
    F,
    /// Unbedenklichkeitsantrag
    U,
    /// Schlussantrag (VN)
    V
}
