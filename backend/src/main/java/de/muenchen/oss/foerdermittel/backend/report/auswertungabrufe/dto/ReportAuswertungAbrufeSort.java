package de.muenchen.oss.foerdermittel.backend.report.auswertungabrufe.dto;

public enum ReportAuswertungAbrufeSort {

    FOERDERBEREICH_PROJEKTNUMMER_ADATUM("order by v_fob_fb asc, v_projnr asc, v_abruf_datum asc"),
    FOERDERBEREICH_EDATUM("order by v_fob_fb asc, v_erh_datum asc"),
    FOERDERBEREICH_ADATUM("order by v_fob_fb asc, v_abruf_datum asc "),
    FOERDERBEREICH_PROJEKTSTRASSE_ADATUM("order by v_fob_fb asc, v_pstrasse asc, v_abruf_datum asc"),
    PROJEKTNUMMER_ADATUM("order by v_projnr asc, v_abruf_datum asc"),
    EDATUM("order by v_erh_datum asc"),
    ADATUM("order by v_abruf_datum asc"),
    PROJEKTSTRASSE("order by v_pstrasse asc");

    private final String orderBy;

    ReportAuswertungAbrufeSort(final String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }
}
