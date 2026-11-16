package de.muenchen.oss.foerdermittel.backend.antrag;

/// Projection of an [Antrag] whose [Antragstyp] may exist only once per Projekt.
public interface UniqueAntrag {

    String getProjnr();

    Antragstyp getAntragstyp();

    Long getId();
}
