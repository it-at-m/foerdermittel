package de.muenchen.oss.foerdermittel.backend.report.auswertungarchiv.dto;

import de.muenchen.oss.foerdermittel.backend.report.util.ReportControllerUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("CPD-START")
public class ReportAuswertungArchivMapper {

    public Map<String, Object> toJasperParameters(
            final ReportAuswertungArchivDTO dto) {

        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("P_BEZ", ReportControllerUtil.toTrimmedStringOrNull(dto.bez()));
        parameters.put("P_FB", ReportControllerUtil.toTrimmedStringOrNull(dto.fb()));
        parameters.put("P_UA", ReportControllerUtil.toTrimmedStringOrNull(dto.ua()));
        parameters.put("P_KURZ", ReportControllerUtil.toTrimmedStringOrNull(dto.kurz()));
        parameters.put("P_PSTRASSE", ReportControllerUtil.toLikeParameter(dto.pstrasse()));
        parameters.put("P_PNAME", ReportControllerUtil.toLikeParameter(dto.pname()));
        parameters.put("P_PROJNR", ReportControllerUtil.toTrimmedStringOrNull(dto.projnr()));
        parameters.put(
                "P_KRISOFP",
                dto.krisofp() != null
                        ? dto.krisofp().name()
                        : null);

        parameters.put("P_FILM", dto.film() ? "1" : "0");
        parameters.put("P_FILM_OHNE", dto.filmohne() ? "1" : "0");
        parameters.put("P_OFFEN", dto.offen() ? "1" : "0");
        parameters.put("P_PLAN_AB", ReportControllerUtil.toTrimmedStringOrNull(dto.planab()));
        parameters.put("P_SP_SEIT", ReportControllerUtil.toTrimmedStringOrNull(dto.spseit()));
        parameters.put("P_OP", dto.op() ? "1" : "0");
        parameters.put("P_MP", dto.mp() ? "1" : "0");

        return parameters;
    }
}
