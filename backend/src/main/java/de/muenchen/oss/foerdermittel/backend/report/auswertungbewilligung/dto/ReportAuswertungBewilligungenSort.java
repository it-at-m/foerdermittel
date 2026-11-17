package de.muenchen.oss.foerdermittel.backend.report.auswertungbewilligung.dto;

public enum ReportAuswertungBewilligungenSort {

    FOERDERBEREICH_PROJEKTNUMMER_BEWILLIGTAM("order by v_fob_fb asc, v_projnr asc, v_bdatum asc"),
    FOERDERBEREICH_STRASSE_PROJEKTNUMMER("order by v_fob_fb asc, v_pstrasse asc, v_pro_projnr asc, V_ANTRAGSDATUM asc"),
    PROJEKTNUMMER_BEWILLIGTAM("order by v_projnr asc, v_bdatum asc"),
    PROJEKTSTRASSE_PROJEKTNUMMER_BEWILLIGTAM("order by v_pstrasse asc, v_projnr asc, v_bdatum asc"),
    BEWILLIGTAM("order by v_bdatum asc");

    private final String orderBy;

    ReportAuswertungBewilligungenSort(final String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }
}
