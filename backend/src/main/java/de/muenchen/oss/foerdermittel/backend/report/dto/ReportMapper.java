package de.muenchen.oss.foerdermittel.backend.report.dto;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/// Component responsible for conversion between report DTOs and Jasper parameter maps.
@Component
public class ReportMapper {

    public Map<String, Object> toJasperParameters(final ReportStichworteDTO dto) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("P_BEREICH", dto.bereich());
        return parameters;
    }

    public Map<String, Object> toJasperParameters(
            final ReportAuswertungProjekteDTO dto) {

        final Map<String, Object> parameters = new HashMap<>();

        if (dto.jahr() == null || dto.jahr().isBlank()) {
            parameters.put("P_JAHR", null);
        } else {
            parameters.put("P_JAHR", dto.jahr());
        }

        parameters.put("P_BEZ", parseIntegerOrNull(dto.bez()));
        parameters.put("P_FB", parseIntegerOrNull(dto.fb()));
        parameters.put("P_UA", dto.ua());
        parameters.put("P_KURZ", dto.kurz());
        parameters.put("P_PSTRASSE", dto.pstrasse());
        parameters.put("P_PNAME", dto.pname());
        parameters.put("P_SGT", parseIntegerOrNull(dto.sgt()));
        parameters.put("P_BPG", parseIntegerOrNull(dto.bpg()));
        parameters.put("P_KRISOFP", dto.krisofp());

        parameters.put("P_KAUF", dto.kauf());
        parameters.put("P_OFFEN", dto.offen());
        parameters.put("P_RELEVANT", dto.relevant());

        parameters.put("P_SBL",  dto.sbl());

        return parameters;
    }

    private Integer parseIntegerOrNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            // Optional: Log oder Fehlerbehandlung
            return null;
        }
    }
}
