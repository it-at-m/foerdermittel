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

    public Map<String, Object> toJasperParameters(final ReportHaushalt1DTO dto) {
        final Map<String, Object> parameters = new HashMap<>();

       parameters.put("P_JAHR", dto.haushaltsjahr());
        if (!dto.fb().isBlank())  parameters.put("P_FB", dto.fb()); else parameters.put("P_FB", null);
        if (!dto.fipo().isBlank())  parameters.put("P_FIPO", dto.fipo()); else parameters.put("P_FIPO", null);
        if (!dto.sbl().isBlank())  parameters.put("P_SBL", dto.sbl()); else parameters.put("P_SBL", null);
        if (!dto.bez().isBlank())  parameters.put("P_BEZ", dto.bez()); else parameters.put("P_BEZ", null);
        parameters.put("P_HH", dto.hh());
        return parameters;
    }

}
