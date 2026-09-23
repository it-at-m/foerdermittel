package de.muenchen.oss.foerdermittel.backend.report.dto;

public enum ReportHaushalt1Sort {

    PROJEKTNUMMER(1, "order by pro_projnr asc"),
    STRASSE(2, "order by p_pstrasse asc, pro_projnr asc"),
    FIPO(3, "order by fipo asc"),
    FB_PROJEKTNUMMER(4, "order by P_FOB_FB, pro_projnr asc"),
    FB_STRASSE_PROJEKTNUMMER(5, "order by P_FOB_FB, p_pstrasse asc, pro_projnr asc"),
    FB_FIPO(6, "order by P_FOB_FB, FIPO asc");

    private final int value;
    private final String orderBy;

    ReportHaushalt1Sort(final int value, final String orderBy) {
        this.value = value;
        this.orderBy = orderBy;
    }

    public int getValue() {
        return value;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public static ReportHaushalt1Sort fromValue(final int value) {
        for (final ReportHaushalt1Sort sort : values()) {
            if (sort.value == value) {
                return sort;
            }
        }

        throw new IllegalArgumentException(
                "Ungültiger Sortierparameter: " + value);
    }
}
