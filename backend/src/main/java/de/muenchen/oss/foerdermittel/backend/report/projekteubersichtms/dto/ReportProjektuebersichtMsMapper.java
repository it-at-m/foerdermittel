package de.muenchen.oss.foerdermittel.backend.report.projekteubersichtms.dto;

import de.muenchen.oss.foerdermittel.backend.report.util.ReportControllerUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ReportProjektuebersichtMsMapper {
    public Map<String, Object> toJasperParameters(
            final ReportProjektuebersichtMsDTO dto) {

        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("P_JAHR", ReportControllerUtil.toTrimmedStringOrNull(dto.jahr()));
        parameters.put("P_BEZ", ReportControllerUtil.toTrimmedStringOrNull(dto.bez()));
        parameters.put("P_SBL", ReportControllerUtil.toTrimmedStringOrNull(dto.sbl()));
        parameters.put("P_FB", ReportControllerUtil.toTrimmedStringOrNull(dto.fb()));
        parameters.put("P_UA", ReportControllerUtil.toTrimmedStringOrNull(dto.ua()));
        parameters.put("P_KURZ", ReportControllerUtil.toTrimmedStringOrNull(dto.kurz()));
        parameters.put("P_OFFEN", ReportControllerUtil.toTrimmedStringOrNull(dto.offen()));
        parameters.put("P_VN", ReportControllerUtil.toTrimmedStringOrNull(dto.vn()));
        parameters.put(
                "P_KRISOFP",
                dto.krisofp() != null
                        ? dto.krisofp().name()
                        : null);
        parameters.put("P_SGT", ReportControllerUtil.toTrimmedStringOrNull(dto.sgt()));
        parameters.put("P_BPG", ReportControllerUtil.toTrimmedStringOrNull(dto.bpg()));

        return parameters;
    }
}
