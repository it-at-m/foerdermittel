package de.muenchen.oss.foerdermittel.backend.report.dto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;
import org.springframework.stereotype.Component;

/// Component responsible for conversion between report DTOs and Jasper parameter maps.
@Component
public class ReportMapper {

    public Map<String, Object> toJasperParameters(
            final ReportStichworteDTO dto) {

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("P_BEREICH", dto.bereich());

        return parameters;
    }

    public Map<String, Object> toJasperParameters(
            final ReportAuswertungProjekteDTO dto) {

        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("P_JAHR", dto.jahr());

        parameters.put(
                "P_SBL",
                StringUtils.hasText(dto.sbl())
                        ? dto.sbl()
                        : null
        );

        parameters.put(
                "P_BEZ",
                StringUtils.hasText(dto.bez())
                        ? dto.bez()
                        : null
        );

        parameters.put(
                "P_FB",
                StringUtils.hasText(dto.fb())
                        ? dto.fb()
                        : null
        );

        parameters.put(
                "P_UA",
                StringUtils.hasText(dto.ua())
                        ? dto.ua()
                        : null
        );

        parameters.put(
                "P_KURZ",
                StringUtils.hasText(dto.kurz())
                        ? dto.kurz()
                        : null
        );

        parameters.put(
                "P_KRISOFP",
                StringUtils.hasText(dto.krisofp())
                        ? dto.krisofp()
                        : null
        );

        parameters.put(
                "P_SGT",
                StringUtils.hasText(dto.sgt())
                        ? dto.sgt()
                        : null
        );

        parameters.put(
                "P_BPG",
                StringUtils.hasText(dto.bpg())
                        ? dto.bpg()
                        : null
        );

        parameters.put("P_PSTRASSE", dto.pstrasse());
        parameters.put("P_PNAME", dto.pname());

        parameters.put("P_KAUF", dto.kauf());
        parameters.put("P_OFFEN", dto.offen());
        parameters.put("P_RELEVANT", dto.relevant());

        return parameters;
    }
}
