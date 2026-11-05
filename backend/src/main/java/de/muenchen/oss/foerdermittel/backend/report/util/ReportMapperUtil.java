package de.muenchen.oss.foerdermittel.backend.report.util;

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
     * Adds common filter parameters to the provided Jasper parameter map
     * used by the evaluation reports.
     *
     * <p>
     * Empty or unset values are processed accordingly to ensure
     * proper handling by JasperReports.
     *
     * @param parameters map containing the Jasper parameters
     * @param fb
     * @param ua
     * @param kurz
     * @param sgt
     * @param bpg
     * @param sbl districtlist
     * @param bez district
     * @param pstrasse street filter
     * @param pname project name
     * @param krisofp special funding program
     * @param offen open status filter
     * @param relevant relevance filter
     */

    public static void addCommonParameters(
            final Map<String, Object> parameters,
            final String fb,
            final String ua,
            final String kurz,
            final String sgt,
            final String bpg,
            final String sbl,
            final String bez,
            final String pstrasse,
            final String pname,
            final Enum<?> krisofp,
            final String offen,
            final String relevant) {

        parameters.put("P_FB", ReportControllerUtil.nullIfBlank(fb));
        parameters.put("P_UA", ReportControllerUtil.nullIfBlank(ua));
        parameters.put("P_KURZ", ReportControllerUtil.nullIfBlank(kurz));
        parameters.put("P_SGT", ReportControllerUtil.nullIfBlank(sgt));
        parameters.put("P_BPG", ReportControllerUtil.nullIfBlank(bpg));
        parameters.put("P_SBL", ReportControllerUtil.nullIfBlank(sbl));
        parameters.put("P_BEZ", ReportControllerUtil.nullIfBlank(bez));
        parameters.put("P_PSTRASSE", ReportControllerUtil.toLikeParameter(pstrasse));
        parameters.put("P_PNAME", ReportControllerUtil.toLikeParameter(pname));
        parameters.put("P_KRISOFP", krisofp != null ? krisofp.name() : null);
        parameters.put("P_OFFEN", ReportControllerUtil.nullIfBlank(offen));
        parameters.put("P_RELEVANT", ReportControllerUtil.nullIfBlank(relevant));
    }
}
