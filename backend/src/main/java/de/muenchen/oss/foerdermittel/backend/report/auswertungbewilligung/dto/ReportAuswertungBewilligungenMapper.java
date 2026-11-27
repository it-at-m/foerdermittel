package de.muenchen.oss.foerdermittel.backend.report.auswertungbewilligung.dto;

import de.muenchen.oss.foerdermittel.backend.report.util.ReportControllerUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("CPD-START")
public class ReportAuswertungBewilligungenMapper {

    public Map<String, Object> toJasperParameters(
            final ReportAuswertungBewilligungenDTO dto) {

        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("P_JAHR_VON", ReportControllerUtil.toTrimmedStringOrNull(dto.jahrVon()));
        parameters.put("P_JAHR_BIS", ReportControllerUtil.toTrimmedStringOrNull(dto.jahrBis()));
        parameters.put("P_BDATUM", ReportControllerUtil.toTrimmedStringOrNull(dto.datum()));
        parameters.put("P_MONAT", ReportControllerUtil.toTrimmedStringOrNull(dto.monat()));
        parameters.put("P_FB", ReportControllerUtil.toTrimmedStringOrNull(dto.fb()));
        parameters.put("P_UA", ReportControllerUtil.toTrimmedStringOrNull(dto.ua()));
        parameters.put("P_KURZ", ReportControllerUtil.toTrimmedStringOrNull(dto.kurz()));
        parameters.put("P_SGT", ReportControllerUtil.toTrimmedStringOrNull(dto.sgt()));
        parameters.put("P_BPG", ReportControllerUtil.toTrimmedStringOrNull(dto.bpg()));
        parameters.put("P_SBL", ReportControllerUtil.toTrimmedStringOrNull(dto.sbl()));
        parameters.put("P_BEZ", ReportControllerUtil.toTrimmedStringOrNull(dto.bez()));
        parameters.put("P_PSTRASSE", ReportControllerUtil.toLikeParameter(dto.pstrasse()));
        parameters.put("P_PNAME", ReportControllerUtil.toLikeParameter(dto.pname()));

        parameters.put(
                "P_KRISOFP",
                dto.krisofp() != null
                        ? dto.krisofp().name()
                        : null);

        parameters.put("P_PROJNR", ReportControllerUtil.toTrimmedStringOrNull(dto.projnr()));
        parameters.put("P_BZUWART", dto.zuwendungsart() != null ? dto.zuwendungsart().name() : null);
        parameters.put("P_FAG", ReportControllerUtil.toTrimmedStringOrNull(dto.fag()));

        parameters.put("P_ERFOLGT", ReportControllerUtil.toTrimmedStringOrNull(dto.erfolgt()));
        parameters.put("P_NOTIZ", ReportControllerUtil.toTrimmedStringOrNull(dto.notizen()));

        return parameters;
    }
}
