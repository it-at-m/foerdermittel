package de.muenchen.oss.foerdermittel.backend.report.fortsetzungsantrag.dto;

import de.muenchen.oss.foerdermittel.backend.report.dto.ReportFortsetzungsantragDTO;
import java.util.HashMap;
import java.util.Map;

import de.muenchen.oss.foerdermittel.backend.report.util.ReportControllerUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class FortsetzungsantragReportMapper {

    public Map<String, Object> toJasperParameters(final ReportFortsetzungsantragDTO dto) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("P_BEZ", ReportControllerUtil.nullIfBlank(dto.bez()));
        parameters.put("P_SBL", ReportControllerUtil.nullIfBlank(dto.sbl()));
        parameters.put("P_OFFEN", dto.ofPro());
        parameters.put("P_FAG", dto.fag());
        return parameters;
    }
}
