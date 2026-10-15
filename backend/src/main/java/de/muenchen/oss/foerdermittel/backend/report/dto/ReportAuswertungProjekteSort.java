package de.muenchen.oss.foerdermittel.backend.report.dto;

public enum ReportAuswertungProjekteSort {

    STADTBEZIRK_STRASSE("order by v_bez_stadtbezirk asc, v_pstrasse asc"),
    PROJEKTNUMMER("order by v_projnr asc"),
    STRASSE_PROJEKTNUMMER("order by v_pstrasse asc, v_projnr asc"),
    FOERDERBEREICH_STRASSE("order by v_fob_fb asc, v_pstrasse asc"),
    FOERDERBEREICH_PROJEKTNUMMER("order by v_fob_fb asc, v_projnr asc");

    private final String orderBy;

    ReportAuswertungProjekteSort(final String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }
}
