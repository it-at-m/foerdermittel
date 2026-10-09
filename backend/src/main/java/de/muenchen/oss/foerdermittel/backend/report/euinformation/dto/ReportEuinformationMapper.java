package de.muenchen.oss.foerdermittel.backend.report.euinformation.dto;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ReportEuinformationMapper {

    public Map<String, Object> toJasperParameters(final ReportEuinformationDTO dto) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("P_INHALT", dto.inhalt());
        parameters.put("P_JAHR_BIS", dto.jahrBis());
        parameters.put("P_JAHR_VON", dto.jahrVon());
        parameters.put("P_PUB", dto.publikation());
        parameters.put("P_REFBEZ", dto.referatBez());
        parameters.put("P_REFID", dto.referatId());
        return parameters;
    }
}
