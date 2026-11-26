package de.muenchen.oss.foerdermittel.backend.report.auswertungarchiv.dto;

public enum ReportAuswertungArchivSort {

    STRASSE_PROJEKTNUMMER("ORDER BY pstrasse ASC, projnr ASC"),
    FOERDERBEREICH_STRASSE("ORDER BY fb ASC, pstrasse ASC"),
    PROJEKTNUMMER("ORDER BY projnr ASC"),
    NOTIZ("ORDER BY s.notizen ASC");

    private final String orderBy;

    ReportAuswertungArchivSort(final String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }
}
