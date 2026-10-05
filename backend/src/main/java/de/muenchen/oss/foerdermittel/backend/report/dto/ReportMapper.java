package de.muenchen.oss.foerdermittel.backend.report.dto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ReportMapper {

    public Map<String, Object> toJasperParameters(
            final ReportStichworteDTO dto) {

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("P_BEREICH", dto.bereich());

        return parameters;
    }

    public Map<String, Object> toJasperParameters(
            final ReportAuswertungProjekteDTO dto) {

        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("P_JAHR", nullIfBlank(dto.jahr()));

        parameters.put("P_FB", nullIfBlank(dto.fb()));
        parameters.put("P_UA", nullIfBlank(dto.ua()));
        parameters.put("P_KURZ", nullIfBlank(dto.kurz()));
        parameters.put("P_SGT", nullIfBlank(dto.sgt()));
        parameters.put("P_BPG", nullIfBlank(dto.bpg()));
        parameters.put("P_SBL", nullIfBlank(dto.sbl()));
        parameters.put("P_BEZ", nullIfBlank(dto.bez()));

        // LIKE-Parameter
        parameters.put("P_PSTRASSE", toLikeParameter(dto.pstrasse()));
        parameters.put("P_PNAME", toLikeParameter(dto.pname()));

        parameters.put(
                "P_KRISOFP",
                dto.krisofp() != null
                        ? dto.krisofp().name()
                        : null);

        parameters.put("P_KAUF", dto.kauf());
        parameters.put("P_OFFEN", dto.offen());
        parameters.put("P_RELEVANT", dto.relevant());

        return parameters;
    }

    private String nullIfBlank(final String value) {
        if (!StringUtils.hasText(value)
                || "null".equalsIgnoreCase(value)) {
            return null;
        }

        return value;
    }

    private String toLikeParameter(final String value) {
        if (!StringUtils.hasText(value)
                || "null".equalsIgnoreCase(value)) {
            return null;
        }

        return "%" + value.trim() + "%";
    }
}
