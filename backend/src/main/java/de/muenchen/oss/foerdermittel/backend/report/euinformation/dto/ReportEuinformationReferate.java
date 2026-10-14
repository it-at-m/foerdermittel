package de.muenchen.oss.foerdermittel.backend.report.euinformation.dto;

import lombok.Getter;

@Getter
public enum ReportEuinformationReferate {

    REFERAT_1 ("1","RAW"),
    REFERAT_2 ("2","RBS"),
    REFERAT_3 ("3","Sozref R 5"),
    REFERAT_4 ("4","RGU 11"),
    REFERAT_6 ("5","RGU Cs"),
    REFERAT_7 ("6","Krh"),
    REFERAT_8 ("7","AWB"),
    REFERAT_9 ("8","SWM"),
    REFERAT_10 ("9","Kulturref"),
    REFERAT_11 ("10","Bauref"),
    REFERAT_12 ("11","Planref"),
    REFERAT_13 ("12","Direktorium"),
    REFERAT_14 ("13", "POR"),
    REFERAT_15 ("14","KVR"),
    REFERAT_16 ("15","Kommref"),
    REFERAT_17 ("16","MSE"),
    REFERAT_18 ("17","Stk"),
    REFERAT_20 ("20","Alle Referate");


    private final String id;
    private final String referatById;



    ReportEuinformationReferate(final String id, final String referatById) {
        this.id = id;
        this.referatById = referatById;
    }

    public static String getReferatById(String id) {
        for (ReportEuinformationReferate referat : values()) {
            if (referat.getId().equals(id)) {
                return referat.getReferatById();
            }
        }
        return null;
    }
}
