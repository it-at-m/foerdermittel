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

        parameters.put("P_JAHR", ReportControllerUtil.nullIfBlank(dto.jahr()));
        parameters.put("P_BEZ", ReportControllerUtil.nullIfBlank(dto.bez()));
        parameters.put("P_SBL", ReportControllerUtil.nullIfBlank(dto.sbl()));
        parameters.put("P_FB", ReportControllerUtil.nullIfBlank(dto.fb()));
        parameters.put("P_UA", ReportControllerUtil.nullIfBlank(dto.ua()));
        parameters.put("P_KURZ", ReportControllerUtil.nullIfBlank(dto.kurz()));
        parameters.put("P_OFFEN", ReportControllerUtil.nullIfBlank(dto.offen()));
        parameters.put("P_VN", ReportControllerUtil.nullIfBlank(dto.vn()));
        parameters.put(
                "P_KRISOFP",
                dto.krisofp() != null
                        ? dto.krisofp().name()
                        : null);
        parameters.put("P_SGT", ReportControllerUtil.nullIfBlank(dto.sgt()));
        parameters.put("P_BPG", ReportControllerUtil.nullIfBlank(dto.bpg()));

        return parameters;
    }
}
