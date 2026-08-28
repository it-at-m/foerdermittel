package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import lombok.Builder;
import java.math.BigDecimal;

@Builder
public record ProjektResponseDTO(
        String projnr,
        BigDecimal fobFb,
        String kurKurzbez,
        String uasUa,
        String jahr,
        String lfdnr1,
        String lfdnr2,
        String pname,
        String pstrasse
) {
}