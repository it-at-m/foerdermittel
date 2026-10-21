package de.muenchen.oss.foerdermittel.backend.report.auswertungprojekt.dto;

import de.muenchen.oss.foerdermittel.backend.report.util.ReportControllerUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/// Component responsible for conversion between report DTOs and Jasper parameter maps.
@Component
public class ReportAuswertungProjekteMapper {

    public Map<String, Object> toJasperParameters(
            final ReportAuswertungProjekteDTO dto) {

        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("P_JAHR", dto.jahr());

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

        parameters.put("P_KAUF", ReportControllerUtil.nullIfBlank(dto.kauf()));
        parameters.put("P_OFFEN", ReportControllerUtil.nullIfBlank(dto.offen()));
        parameters.put("P_RELEVANT", ReportControllerUtil.nullIfBlank(dto.relevant()));

        return parameters;
    }
}
