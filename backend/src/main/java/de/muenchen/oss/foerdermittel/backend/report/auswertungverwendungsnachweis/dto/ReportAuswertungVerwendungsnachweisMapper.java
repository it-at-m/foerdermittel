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

        parameters.put("P_JAHR", ReportControllerUtil.nullIfBlank(dto.jahr()));
        parameters.put("P_MONAT", ReportControllerUtil.nullIfBlank(dto.monat()));
        parameters.put("P_BEZ", ReportControllerUtil.nullIfBlank(dto.bez()));
        parameters.put("P_SBL", ReportControllerUtil.nullIfBlank(dto.sbl()));
        parameters.put("P_FB", ReportControllerUtil.nullIfBlank(dto.fb()));
        parameters.put("P_UA", ReportControllerUtil.nullIfBlank(dto.ua()));
        parameters.put("P_KURZ", ReportControllerUtil.nullIfBlank(dto.kurz()));
        parameters.put(
                "P_KRISOFP",
                dto.krisofp() != null
                        ? dto.krisofp().name()
                        : null);
        parameters.put("P_SGT", ReportControllerUtil.nullIfBlank(dto.sgt()));
        parameters.put("P_BPG", ReportControllerUtil.nullIfBlank(dto.bpg()));
        parameters.put("P_KRH", ReportControllerUtil.nullIfBlank(dto.krh()));
        parameters.put("P_PSTRASSE", ReportControllerUtil.toLikeParameter(dto.pstrasse()));
        parameters.put("P_PNAME", ReportControllerUtil.toLikeParameter(dto.pname()));
        parameters.put("P_NOTIZ", ReportControllerUtil.nullIfBlank(dto.notizen()));
        parameters.put("P_OHNE_SCHLUSS", ReportControllerUtil.nullIfBlank(dto.ohneschluss()));
        parameters.put("P_FAG", ReportControllerUtil.nullIfBlank(dto.fag()));
        parameters.put("P_WIN", ReportControllerUtil.nullIfBlank(dto.win()));

        return parameters;
    }
}
