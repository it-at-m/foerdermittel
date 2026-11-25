package de.muenchen.oss.foerdermittel.backend.report.verwendungsnachweis.dto;

public enum ReportVerwendungsnachweisSort {
    FBPROJNR("order by v_fob_fb asc, v_projnr asc"),
    FBSTRASSE("order by v_fob_fb asc, v_pstrasse asc"),
    STRASSENAME("order by v_pstrasse asc, v_projnr asc"),
    PROJEKTNUMMER("order by v_projnr asc");

    private final String orderBy;

    ReportVerwendungsnachweisSort(final String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }
}
