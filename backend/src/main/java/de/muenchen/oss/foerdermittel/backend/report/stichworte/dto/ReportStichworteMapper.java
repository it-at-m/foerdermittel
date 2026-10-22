package de.muenchen.oss.foerdermittel.backend.report.stichworte.dto;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/// Component responsible for conversion between report DTOs and Jasper parameter maps.
@Component
public class ReportStichworteMapper {

    public Map<String, Object> toJasperParameters(
            final ReportStichworteDTO dto) {

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("P_BEREICH", dto.bereich());

        return parameters;
    }

}
