package de.muenchen.oss.foerdermittel.backend.report.auswertungabrufe.dto;

import de.muenchen.oss.foerdermittel.backend.report.util.ReportControllerUtil;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("CPD-START")
public class ReportAuswertungAbrufeMapper {

    public Map<String, Object> toJasperParameters(
            final ReportAuswertungAbrufeDTO dto) {

        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("P_JAHR_A_VON", ReportControllerUtil.nullIfBlank(dto.jahrAbrufVon()));
        parameters.put("P_JAHR_A_BIS", ReportControllerUtil.nullIfBlank(dto.jahrAbrufBis()));
        parameters.put("P_JAHR_E_VON", ReportControllerUtil.nullIfBlank(dto.jahrErhaltVon()));
        parameters.put("P_JAHR_E_BIS", ReportControllerUtil.nullIfBlank(dto.jahrErhaltBis()));

        parameters.put("P_FB", ReportControllerUtil.nullIfBlank(dto.fb()));
        parameters.put("P_UA", ReportControllerUtil.nullIfBlank(dto.ua()));
        parameters.put("P_KURZ", ReportControllerUtil.nullIfBlank(dto.kurz()));
        parameters.put("P_SGT", ReportControllerUtil.nullIfBlank(dto.sgt()));
        parameters.put("P_BPG", ReportControllerUtil.nullIfBlank(dto.bpg()));
        parameters.put("P_SBL", ReportControllerUtil.nullIfBlank(dto.sbl()));
        parameters.put("P_BEZ", ReportControllerUtil.nullIfBlank(dto.bez()));
        parameters.put("P_PSTRASSE", ReportControllerUtil.toLikeParameter(dto.pstrasse()));
        parameters.put("P_PNAME", ReportControllerUtil.toLikeParameter(dto.pname()));

        parameters.put(
                "P_KRISOFP",
                dto.krisofp() != null
                        ? dto.krisofp().name()
                        : null);

        parameters.put("P_OFFEN", ReportControllerUtil.nullIfBlank(dto.offen()));

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
