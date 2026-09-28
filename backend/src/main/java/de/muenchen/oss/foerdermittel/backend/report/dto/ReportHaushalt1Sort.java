package de.muenchen.oss.foerdermittel.backend.report.dto;

public enum ReportHaushalt1Sort {

    PROJEKTNUMMER("order by pro_projnr asc"),
    STRASSE("order by p_pstrasse asc, pro_projnr asc"),
    FIPO("order by fipo asc"),
    FB_PROJEKTNUMMER("order by P_FOB_FB, pro_projnr asc"),
    FB_STRASSE_PROJEKTNUMMER("order by P_FOB_FB, p_pstrasse asc, pro_projnr asc"),
    FB_FIPO("order by P_FOB_FB, FIPO asc");

    private final String orderBy;

    ReportHaushalt1Sort(final String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }
}
