package de.muenchen.oss.foerdermittel.backend.report.auswertungtermine.dto;

public enum ReportAuswertungTermineSort {

    PROJEKTNUMMER("order by pro_projnr asc"),
    TERMINE("order by termin asc");

    private final String orderBy;

    ReportAuswertungTermineSort(final String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }
}
