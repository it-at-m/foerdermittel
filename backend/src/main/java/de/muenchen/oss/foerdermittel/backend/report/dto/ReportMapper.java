package de.muenchen.oss.foerdermittel.backend.report.dto;

import java.util.HashMap;
import java.util.Map;


import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/// Component responsible for conversion between report DTOs and Jasper parameter maps.
@Component
public class ReportMapper {

    public Map<String, Object> toJasperParameters(final ReportStichworteDTO dto) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("P_BEREICH", dto.bereich());
        return parameters;
    }

    public Map<String, Object> toJasperParameters(final ReportFortsetzungsantragDTO dto) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("P_BEZ", nullIfBlank(dto.bez()));
        parameters.put("P_SBL", nullIfBlank(dto.sbl()));
        parameters.put("P_OFFEN", dto.ofPro());
        parameters.put("P_FAG", dto.fag());
        return parameters;
    }

    public Map<String, Object> toJasperParameters(final ReportHaushalt1DTO dto) {
        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("P_JAHR", dto.haushaltsjahr());
        if (StringUtils.hasText(dto.fb())) {
            parameters.put("P_FB", dto.fb());
        } else {
            parameters.put("P_FB", null);
        }
        if (StringUtils.hasText(dto.fipo())) {
            parameters.put("P_FIPO", dto.fipo());
        } else {
            parameters.put("P_FIPO", null);
        }
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
        parameters.put("P_HH", dto.hh());
        return parameters;
    }

    private String nullIfBlank(final String value) {
        return StringUtils.hasText(value) ? value : null;
    }

}
