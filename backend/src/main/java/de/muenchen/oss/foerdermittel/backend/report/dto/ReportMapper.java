package de.muenchen.oss.foerdermittel.backend.report.dto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/// Component responsible for conversion between report DTOs and Jasper parameter maps.
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

        // Eingabe-Filter
        parameters.put("P_JAHR", dto.jahr());

        if (StringUtils.hasText(dto.fb())) {
            parameters.put("P_FB", dto.fb());
        } else {
            parameters.put("P_FB", null);
        }

        if (StringUtils.hasText(dto.ua())) {
            parameters.put("P_UA", dto.ua());
        } else {
            parameters.put("P_UA", null);
        }

        if (StringUtils.hasText(dto.kurz())) {
            parameters.put("P_KURZ", dto.kurz());
        } else {
            parameters.put("P_KURZ", null);
        }

        parameters.put("P_KRISOFP", dto.krisofp());

        if (StringUtils.hasText(dto.sgt())) {
            parameters.put("P_SGT", dto.sgt());
        } else {
            parameters.put("P_SGT", null);
        }

        if (StringUtils.hasText(dto.bpg())) {
            parameters.put("P_BPG", dto.bpg());
        } else {
            parameters.put("P_BPG", null);
        }

        parameters.put("P_PSTRASSE", dto.pstrasse());
        parameters.put("P_PNAME", dto.pname());

        if (StringUtils.hasText(dto.sbl())) {
            parameters.put("P_SBL", dto.sbl());
        } else {
            parameters.put("P_SBL", null);
        }

        if (StringUtils.hasText(dto.bez())) {
            parameters.put("P_BEZ", dto.bez());
        } else {
            parameters.put("P_BEZ", null);
        }

        parameters.put("P_KAUF", dto.kauf());
        parameters.put("P_OFFEN", dto.offen());
        parameters.put("P_RELEVANT", dto.relevant());

        return parameters;
    }
}
