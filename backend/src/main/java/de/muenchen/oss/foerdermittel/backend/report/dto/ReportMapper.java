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

    public Map<String, Object> toJasperParameters(final ReportProjektuebersichtDTO dto) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("P_PROJNR", dto.projnr());
        parameters.put("P_NOTIZ", dto.notiz() ? "1" : "0");
        return parameters;
    }

}
