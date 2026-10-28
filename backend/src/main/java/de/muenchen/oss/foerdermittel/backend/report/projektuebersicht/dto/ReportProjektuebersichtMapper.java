package de.muenchen.oss.foerdermittel.backend.report.projektuebersicht.dto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ReportProjektuebersichtMapper {

    public Map<String, Object> toJasperParameters(final ReportProjektuebersichtDTO dto) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("P_PROJNR", dto.projnr());
        parameters.put("P_NOTIZ", dto.notiz() ? "1" : "0");
        return parameters;
    }
}
