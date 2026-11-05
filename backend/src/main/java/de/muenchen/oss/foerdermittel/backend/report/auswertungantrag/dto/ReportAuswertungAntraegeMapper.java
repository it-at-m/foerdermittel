package de.muenchen.oss.foerdermittel.backend.report.auswertungantrag.dto;

import de.muenchen.oss.foerdermittel.backend.report.util.ReportControllerUtil;
import de.muenchen.oss.foerdermittel.backend.report.util.ReportMapperUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ReportAuswertungAntraegeMapper {

    public Map<String, Object> toJasperParameters(
            final ReportAuswertungAntraegeDTO dto) {

        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("P_JAHR_VON", ReportControllerUtil.nullIfBlank(dto.jahrvon()));
        parameters.put("P_JAHR_BIS", ReportControllerUtil.nullIfBlank(dto.jahrbis()));

        ReportMapperUtil.addCommonParameters(
                parameters,
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
                dto.relevant());

        parameters.put("P_PROJNR", ReportControllerUtil.nullIfBlank(dto.projnr()));
        parameters.put("P_ATYP", dto.atyp() != null ? dto.atyp().name() : null);
        parameters.put("P_FAG", ReportControllerUtil.nullIfBlank(dto.fag()));

        return parameters;
    }
}
