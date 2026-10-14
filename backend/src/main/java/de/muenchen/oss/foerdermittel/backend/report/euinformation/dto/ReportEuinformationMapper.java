package de.muenchen.oss.foerdermittel.backend.report.euinformation.dto;

import de.muenchen.oss.foerdermittel.backend.report.util.ReportControllerUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ReportEuinformationMapper {

    public Map<String, Object> toJasperParameters(final ReportEuinformationDTO dto) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("P_INHALT", ReportControllerUtil.toLikeParameter(dto.inhalt()));
        parameters.put("P_JAHR_BIS", ReportControllerUtil.nullIfBlank(dto.jahrBis()));
        parameters.put("P_JAHR_VON", ReportControllerUtil.nullIfBlank(dto.jahrVon()));
        parameters.put("P_PUB", dto.publikation());
        parameters.put("P_REFBEZ", ReportEuinformationReferate.getReferatById(dto.referatId()));
        parameters.put("P_REFID", dto.referatId());
        return parameters;
    }
}
