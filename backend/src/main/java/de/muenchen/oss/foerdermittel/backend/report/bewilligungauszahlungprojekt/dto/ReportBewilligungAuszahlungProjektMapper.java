package de.muenchen.oss.foerdermittel.backend.report.bewilligungauszahlungprojekt.dto;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ReportBewilligungAuszahlungProjektMapper {

    public Map<String, Object> toJasperParameters(final ReportBewilligungAuszahlungProjektDTO dto) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("P_PROJNR", dto.projnr());
        parameters.put("P_ERFOLGT", dto.erfolgt() ? "1" : "0");
        return parameters;
    }
}
