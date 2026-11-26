package de.muenchen.oss.foerdermittel.backend.report.auswertungverwendungsnachweis.dto;

import de.muenchen.oss.foerdermittel.backend.report.util.ReportControllerUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ReportAuswertungVerwendungsnachweisMapper {
    public Map<String, Object> toJasperParameters(
            final ReportAuswertungVerwendungsnachweisDTO dto) {

        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("P_JAHR", ReportControllerUtil.toTrimmedStringOrNull(dto.jahr()));
        parameters.put("P_MONAT", ReportControllerUtil.toTrimmedStringOrNull(dto.monat()));
        parameters.put("P_BEZ", ReportControllerUtil.toTrimmedStringOrNull(dto.bez()));
        parameters.put("P_SBL", ReportControllerUtil.toTrimmedStringOrNull(dto.sbl()));
        parameters.put("P_FB", ReportControllerUtil.toTrimmedStringOrNull(dto.fb()));
        parameters.put("P_UA", ReportControllerUtil.toTrimmedStringOrNull(dto.ua()));
        parameters.put("P_KURZ", ReportControllerUtil.toTrimmedStringOrNull(dto.kurz()));
        parameters.put(
                "P_KRISOFP",
                dto.krisofp() != null
                        ? dto.krisofp().name()
                        : null);
        parameters.put("P_SGT", ReportControllerUtil.toTrimmedStringOrNull(dto.sgt()));
        parameters.put("P_BPG", ReportControllerUtil.toTrimmedStringOrNull(dto.bpg()));
        parameters.put("P_KRH", ReportControllerUtil.toTrimmedStringOrNull(dto.krh()));
        parameters.put("P_PSTRASSE", ReportControllerUtil.toLikeParameter(dto.pstrasse()));
        parameters.put("P_PNAME", ReportControllerUtil.toLikeParameter(dto.pname()));
        parameters.put("P_NOTIZ", ReportControllerUtil.toTrimmedStringOrNull(dto.notizen()));
        parameters.put("P_OHNE_SCHLUSS", ReportControllerUtil.toTrimmedStringOrNull(dto.ohneschluss()));
        parameters.put("P_FAG", ReportControllerUtil.toTrimmedStringOrNull(dto.fag()));
        parameters.put("P_WIN", ReportControllerUtil.toTrimmedStringOrNull(dto.win()));

        return parameters;
    }
}
