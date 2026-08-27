package de.muenchen.oss.foerdermittel.backend.report.dto;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * Component responsible for conversion between report DTOs and Jasper parameter maps.
 */
@Component
public class ReportMapper {

    public Map<String, Object> toJasperParameters(final ReportStichwortbereicheDTO dto) {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("P_BEREICH", dto.bereich());

        return parameters;
    }

}
