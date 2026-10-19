package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ProjektResponseDTO(
        @NotNull String projnr,
        @NotNull String foerderbereich,
        @NotNull String kurKurzbez,
        @NotNull String uasUa,
        @NotNull String jahr,
        @NotNull String lfdnr1,
        @NotNull String lfdnr2,
        String pname,
        String pstrasse) {
}
