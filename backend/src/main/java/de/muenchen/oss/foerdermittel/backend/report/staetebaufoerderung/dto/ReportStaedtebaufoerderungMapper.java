package de.muenchen.oss.foerdermittel.backend.report.staetebaufoerderung.dto;

import de.muenchen.oss.foerdermittel.backend.report.util.ReportControllerUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ReportStaedtebaufoerderungMapper {

    public Map<String, Object> toJasperParameters(final ReportStaedtebaufoerderungDTO dto) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("P_JAHR_BIS", ReportControllerUtil.nullIfBlank(dto.jahrBis()));
        parameters.put("P_JAHR_VON", ReportControllerUtil.nullIfBlank(dto.jahrVon()));
        parameters.put("P_PNAME", ReportControllerUtil.toLikeParameter(dto.pname()));
        parameters.put("P_TRAEGER", ReportControllerUtil.nullIfBlank(dto.traeger()));
        parameters.put("P_NOTIZ", dto.notizen());
        return parameters;
    }
}
