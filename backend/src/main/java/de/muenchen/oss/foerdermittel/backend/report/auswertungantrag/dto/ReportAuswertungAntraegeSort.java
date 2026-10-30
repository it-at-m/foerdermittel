package de.muenchen.oss.foerdermittel.backend.report.auswertungantrag.dto;

public enum ReportAuswertungAntraegeSort {

    PROJEKTSTRASSE_PROJEKTNUMMER("order by v_pstrasse asc, v_pro_projnr asc, V_ANTRAGSDATUM asc"),
    PROJEKTNUMMER("order by v_pstrasse asc, v_pro_projnr asc, V_ANTRAGSDATUM asc"),
    FOERDERBEREICH_PROJEKTNUMMER("order by v_fob_fb asc, v_pro_projnr asc, V_ANTRAGSDATUM asc"),
    FOERDERBEREICH_STRASSE("order by v_fob_fb asc, v_pstrasse asc, v_pro_projnr asc, V_ANTRAGSDATUM asc");

    private final String orderBy;

    ReportAuswertungAntraegeSort(final String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }
}
