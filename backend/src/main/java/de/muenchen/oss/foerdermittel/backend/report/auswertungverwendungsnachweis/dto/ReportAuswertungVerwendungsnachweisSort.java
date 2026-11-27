package de.muenchen.oss.foerdermittel.backend.report.auswertungverwendungsnachweis.dto;

public enum ReportAuswertungVerwendungsnachweisSort {
    FOERDERBEREICH_PROJEKTNUMMER("order by v_fob_fb asc, v_projnr asc"),
    FOERDERBEREICH_STRASSE("order by v_fob_fb asc, v_pstrasse asc"),
    PROJEKTSTRASSE_PROJEKTNUMMER("order by v_pstrasse asc, v_projnr asc"),
    PROJEKTNUMMER("order by v_projnr asc");

    private final String orderBy;

    ReportAuswertungVerwendungsnachweisSort(final String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }
}
