package de.muenchen.oss.foerdermittel.backend.report.auswertungprojekt.dto;

import de.muenchen.oss.foerdermittel.backend.report.util.ReportControllerUtil;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ReportAuswertungProjekteMapper {

    public Map<String, Object> toJasperParameters(
            final ReportAuswertungProjekteDTO dto) {

        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("P_JAHR", ReportControllerUtil.toTrimmedStringOrNull(dto.jahr()));

        parameters.put("P_FB", ReportControllerUtil.toTrimmedStringOrNull(dto.fb()));
        parameters.put("P_UA", ReportControllerUtil.toTrimmedStringOrNull(dto.ua()));
        parameters.put("P_KURZ", ReportControllerUtil.toTrimmedStringOrNull(dto.kurz()));
        parameters.put("P_SGT", ReportControllerUtil.toTrimmedStringOrNull(dto.sgt()));
        parameters.put("P_BPG", ReportControllerUtil.toTrimmedStringOrNull(dto.bpg()));
        parameters.put("P_SBL", ReportControllerUtil.toTrimmedStringOrNull(dto.sbl()));
        parameters.put("P_BEZ", ReportControllerUtil.toTrimmedStringOrNull(dto.bez()));
        parameters.put("P_PSTRASSE", ReportControllerUtil.toLikeParameter(dto.pstrasse()));
        parameters.put("P_PNAME", ReportControllerUtil.toLikeParameter(dto.pname()));

        parameters.put(
                "P_KRISOFP",
                dto.krisofp() != null
                        ? dto.krisofp().name()
                        : null);

        parameters.put("P_OFFEN", ReportControllerUtil.toTrimmedStringOrNull(dto.offen()));
        parameters.put("P_RELEVANT", ReportControllerUtil.toTrimmedStringOrNull(dto.relevant()));
        parameters.put("P_KAUF", ReportControllerUtil.toTrimmedStringOrNull(dto.kauf()));

        return parameters;
    }
}
