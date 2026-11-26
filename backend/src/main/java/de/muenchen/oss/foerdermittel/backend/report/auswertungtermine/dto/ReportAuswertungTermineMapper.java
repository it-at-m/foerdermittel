package de.muenchen.oss.foerdermittel.backend.report.auswertungtermine.dto;

import de.muenchen.oss.foerdermittel.backend.report.util.ReportControllerUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("CPD-START")
public class ReportAuswertungTermineMapper {

    public Map<String, Object> toJasperParameters(
            final ReportAuswertungTermineDTO dto) {

        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("P_JAHR", ReportControllerUtil.nullIfBlank(dto.jahr()));
        parameters.put("P_MONAT", dto.monat() == null ? null : String.format("%02d", dto.monat()));

        parameters.put("P_FB", ReportControllerUtil.nullIfBlank(dto.fb()));
        parameters.put("P_UA", ReportControllerUtil.nullIfBlank(dto.ua()));
        parameters.put("P_KURZ", ReportControllerUtil.nullIfBlank(dto.kurz()));
        parameters.put("P_SBL", ReportControllerUtil.nullIfBlank(dto.sbl()));
        parameters.put("P_BEZ", ReportControllerUtil.nullIfBlank(dto.bez()));
        parameters.put("P_PSTRASSE", ReportControllerUtil.toLikeParameter(dto.pstrasse()));
        parameters.put("P_PNAME", ReportControllerUtil.toLikeParameter(dto.pname()));
        parameters.put("P_ZUKUNFT", dto.zukunft() ? "1" : "0");
        parameters.put("P_UEB", dto.ueb() ? "1" : "0");

        return parameters;
    }
}
