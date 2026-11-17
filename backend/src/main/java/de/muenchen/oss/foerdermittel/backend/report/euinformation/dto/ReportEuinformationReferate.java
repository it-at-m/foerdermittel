package de.muenchen.oss.foerdermittel.backend.report.euinformation.dto;

import lombok.Getter;

@Getter
public enum ReportEuinformationReferate {

    REFERAT_1("1", "RAW"),
    REFERAT_2("2", "RBS"),
    REFERAT_3("3", "Sozref R 5"),
    REFERAT_4("4", "RGU 11"),
    REFERAT_5("5", "RGU Cs"),
    REFERAT_6("6", "Krh"),
    REFERAT_7("7", "AWB"),
    REFERAT_8("8", "SWM"),
    REFERAT_9("9", "Kulturref"),
    REFERAT_10("10", "Bauref"),
    REFERAT_11("11", "Planref"),
    REFERAT_12("12", "Direktorium"),
    REFERAT_13("13", "POR"),
    REFERAT_14("14", "KVR"),
    REFERAT_15("15", "Kommref"),
    REFERAT_16("16", "MSE"),
    REFERAT_17("17", "Stk"),
    REFERAT_20("20", "Alle Referate");

    private final String id;
    private final String referatById;

    ReportEuinformationReferate(final String id, final String referatById) {
        this.id = id;
        this.referatById = referatById;
    }

    public static String getReferatById(final String id) {
        for (final ReportEuinformationReferate referat : values()) {
            if (referat.getId().equals(id)) {
                return referat.getReferatById();
            }
        }
        return null;
    }
}
