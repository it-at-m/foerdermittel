package de.muenchen.oss.foerdermittel.backend.report.util;

import de.muenchen.oss.foerdermittel.backend.report.util.dto.CommonReportParameters;

import java.util.Map;

/**
 * Utility class providing common Jasper parameters for different
 * report mapper implementations.
 * used for ReportAuswertungAntraege, ReportAuswertungProjekte
 */

public final class ReportMapperUtil {

    private ReportMapperUtil() {
    }

    /**
     * Adds common filter parameters to the provided Jasper parameter map.
     *
     * @param parameters map containing the Jasper parameters
     * @param commonParameters common search parameters
     */
    public static void addCommonParameters(
            final Map<String, Object> parameters,
            final CommonReportParameters commonParameters) {

        parameters.put("P_FB", ReportControllerUtil.nullIfBlank(commonParameters.fb()));
        parameters.put("P_UA", ReportControllerUtil.nullIfBlank(commonParameters.ua()));
        parameters.put("P_KURZ", ReportControllerUtil.nullIfBlank(commonParameters.kurz()));
        parameters.put("P_SGT", ReportControllerUtil.nullIfBlank(commonParameters.sgt()));
        parameters.put("P_BPG", ReportControllerUtil.nullIfBlank(commonParameters.bpg()));
        parameters.put("P_SBL", ReportControllerUtil.nullIfBlank(commonParameters.sbl()));
        parameters.put("P_BEZ", ReportControllerUtil.nullIfBlank(commonParameters.bez()));
        parameters.put("P_PSTRASSE", ReportControllerUtil.toLikeParameter(commonParameters.pstrasse()));
        parameters.put("P_PNAME", ReportControllerUtil.toLikeParameter(commonParameters.pname()));

        parameters.put(
                "P_KRISOFP",
                commonParameters.krisofp() != null
                        ? commonParameters.krisofp().name()
                        : null);

        parameters.put("P_OFFEN", ReportControllerUtil.nullIfBlank(commonParameters.offen()));
        parameters.put("P_RELEVANT", ReportControllerUtil.nullIfBlank(commonParameters.relevant()));
    }
}
