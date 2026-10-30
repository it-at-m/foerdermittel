package de.muenchen.oss.foerdermittel.backend.report.annahmeanordnungsap.dto;


import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ReportAnnahmeAnordnungSapMapper {

    public Map<String, Object> toJasperParameters(final ReportAnnahmeAnordnungSapDTO dto) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("P_PROJNR", dto.projnr());
        return parameters;
    }
}
