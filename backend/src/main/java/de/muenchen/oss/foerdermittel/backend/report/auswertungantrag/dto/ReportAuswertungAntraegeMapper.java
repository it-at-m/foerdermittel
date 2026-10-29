package de.muenchen.oss.foerdermittel.backend.report.auswertungantrag.dto;

import de.muenchen.oss.foerdermittel.backend.report.util.ReportControllerUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ReportAuswertungAntraegeMapper {

    public Map<String, Object> toJasperParameters(
            final ReportAuswertungAntraegeDTO dto) {

        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("P_JAHR_VON", ReportControllerUtil.nullIfBlank(dto.jahrvon()));
        parameters.put("P_JAHR_BIS", ReportControllerUtil.nullIfBlank(dto.jahrbis()));
        parameters.put("P_FB", ReportControllerUtil.nullIfBlank(dto.fb()));
        parameters.put("P_UA", ReportControllerUtil.nullIfBlank(dto.ua()));
        parameters.put("P_KURZ", ReportControllerUtil.nullIfBlank(dto.kurz()));
        parameters.put("P_SGT", ReportControllerUtil.nullIfBlank(dto.sgt()));
        parameters.put("P_BPG", ReportControllerUtil.nullIfBlank(dto.bpg()));
        parameters.put("P_SBL", ReportControllerUtil.nullIfBlank(dto.sbl()));
        parameters.put("P_BEZ", ReportControllerUtil.nullIfBlank(dto.bez()));

        parameters.put("P_PSTRASSE", ReportControllerUtil.toLikeParameter(dto.pstrasse()));
        parameters.put("P_PNAME", ReportControllerUtil.toLikeParameter(dto.pname()));

        parameters.put(
                "P_KRISOFP",
                dto.krisofp() != null
                        ? dto.krisofp().name()
                        : null);
        parameters.put(
                "P_ATYP" , dto.atyp() != null
                        ? dto.atyp().name()
                        : null);
        parameters.put("P_FAG", ReportControllerUtil.nullIfBlank(dto.kauf()));
        parameters.put("P_KAUF", ReportControllerUtil.nullIfBlank(dto.kauf()));
        parameters.put("P_OFFEN", ReportControllerUtil.nullIfBlank(dto.offen()));
        parameters.put("P_RELEVANT", ReportControllerUtil.nullIfBlank(dto.relevant()));

        return parameters;
    }
}
