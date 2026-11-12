package de.muenchen.oss.foerdermittel.backend.report.auswertungabrufe.dto;

import de.muenchen.oss.foerdermittel.backend.report.util.ReportControllerUtil;
import de.muenchen.oss.foerdermittel.backend.report.util.ReportMapperUtil;
import de.muenchen.oss.foerdermittel.backend.report.util.dto.CommonReportParameters;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ReportAuswertungAbrufeMapper {

    public Map<String, Object> toJasperParameters(
            final ReportAuswertungAbrufeDTO dto) {

        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("P_JAHR_A_VON", ReportControllerUtil.nullIfBlank(dto.jahrAbrufVon()));
        parameters.put("P_JAHR_A_BIS", ReportControllerUtil.nullIfBlank(dto.jahrAbrufBis()));
        parameters.put("P_JAHR_E_VON", ReportControllerUtil.nullIfBlank(dto.jahrErhaltVon()));
        parameters.put("P_JAHR_E_BIS", ReportControllerUtil.nullIfBlank(dto.jahrErhaltBis()));

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
                        dto.offen()));

        parameters.put("P_PROJNR", ReportControllerUtil.nullIfBlank(dto.projnr()));
        parameters.put("P_FAG", ReportControllerUtil.nullIfBlank(dto.fag()));
        parameters.put("P_ERH", ReportControllerUtil.nullIfBlank(dto.erh()));
        parameters.put("P_NICHT_ERH", ReportControllerUtil.nullIfBlank(dto.nichterh()));
        parameters.put("P_OHNE_BEWILL", ReportControllerUtil.nullIfBlank(dto.ohnebewill()));
        parameters.put("P_REF", ReportControllerUtil.nullIfBlank(dto.ref()));
        parameters.put("P_KRH", ReportControllerUtil.nullIfBlank(dto.krh()));

        return parameters;
    }
}
