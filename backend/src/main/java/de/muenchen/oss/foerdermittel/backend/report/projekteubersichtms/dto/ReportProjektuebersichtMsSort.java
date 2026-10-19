package de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.dto;


import lombok.Getter;
@Getter
public enum ReportProjektuebersichtMsSort {
    PROJEKTNUMMER("order by v_projnr asc"),
    STRASSEPROJNR("order by v_pstrasse asc, v_projnr asc"),
    FBPROJNR("order by v_fob_fb asc, v_projnr asc");


    private final String orderBy;

    ReportProjektuebersichtMsSort(final String orderBy) {
        this.orderBy = orderBy;
    }
}
