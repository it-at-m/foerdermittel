package de.muenchen.oss.foerdermittel.backend.report.euinformation.dto;

public enum ReportEuinformationSort {

    PUBLIKATION("order by v_pub_kurzform asc, v_inhalt asc"),
    JAHR("order by v_Jahr asc, v_pub_kurzform asc, v_inhalt asc"),
    REFERAT("order by V_REFBEZ asc, v_pub_kurzform asc, v_inhalt asc"),
    REFERAT_JAHR("order by V_REFBEZ asc, v_Jahr asc, v_pub_kurzform asc, v_inhalt asc");

    private final String orderBy;

    ReportEuinformationSort(final String orderBy) {
        this.orderBy = orderBy;
    }
}
