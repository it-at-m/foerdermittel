package de.muenchen.oss.foerdermittel.backend.report.auswertungantrag.dto;

import de.muenchen.oss.foerdermittel.backend.report.util.ReportControllerUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("CPD-START")
public class ReportAuswertungAntraegeMapper {

    public Map<String, Object> toJasperParameters(
            final ReportAuswertungAntraegeDTO dto) {

        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("P_JAHR_VON", ReportControllerUtil.toTrimmedStringOrNull(dto.jahrVon()));
        parameters.put("P_JAHR_BIS", ReportControllerUtil.toTrimmedStringOrNull(dto.jahrBis()));

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

        parameters.put("P_OFFEN", ReportControllerUtil.toTrimmedStringOrNull(dto.offen()));
        parameters.put("P_RELEVANT", ReportControllerUtil.toTrimmedStringOrNull(dto.relevant()));
        parameters.put("P_PROJNR", ReportControllerUtil.toTrimmedStringOrNull(dto.projnr()));
        parameters.put("P_ATYP", dto.antragstyp() != null ? dto.antragstyp().name() : null);
        parameters.put("P_FAG", ReportControllerUtil.toTrimmedStringOrNull(dto.fag()));

        return parameters;
    }
}
