package de.muenchen.oss.foerdermittel.backend.report.auswertungprojekt.dto;

import de.muenchen.oss.foerdermittel.backend.report.util.ReportControllerUtil;
import de.muenchen.oss.foerdermittel.backend.report.util.ReportMapperUtil;
import de.muenchen.oss.foerdermittel.backend.report.util.dto.CommonReportParameters;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ReportAuswertungProjekteMapper {

    public Map<String, Object> toJasperParameters(
            final ReportAuswertungProjekteDTO dto) {

        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("P_JAHR", ReportControllerUtil.nullIfBlank(dto.jahr()));

        ReportMapperUtil.addCommonParameters(
                parameters,
                new CommonReportParameters(
                        dto.fb(),
                        dto.ua(),
                        dto.kurz(),
                        dto.sgt(),
                        dto.bpg(),
                        dto.sbl(),
                        dto.bez(),
                        dto.pstrasse(),
                        dto.pname(),
                        dto.krisofp(),
                        dto.offen(),
                        dto.relevant()));

        parameters.put("P_KAUF", ReportControllerUtil.nullIfBlank(dto.kauf()));

        return parameters;
    }
}
