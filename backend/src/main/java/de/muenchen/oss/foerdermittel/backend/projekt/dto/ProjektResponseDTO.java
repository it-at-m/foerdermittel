package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import lombok.Builder;
import java.math.BigDecimal;

@Builder
public record ProjektResponseDTO(
        String projnr,
        BigDecimal fobFb,
        String kurKurzbez,
        String uasUa,
        String pname,
        String pstrasse
) {
}