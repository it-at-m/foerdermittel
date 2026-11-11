
package de.muenchen.oss.foerdermittel.backend.hhplan.dto;

import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.dto.StadtbezirkslisteResponseDTO;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record HhplanResponseDTO(
        @NotNull String id,
        @NotNull String proProjnr,
        @NotNull BigDecimal hhjJahr,
        @NotNull String fipo,
        String notizen,
        @NotNull String pname,
        @NotNull String pstrasse,
        @NotNull BigDecimal fobFb,
        BigDecimal mitteleinplanung,
        BigDecimal bewilligungVoj,
        BigDecimal bewilligungHhj,
        BigDecimal erhaltenVoj,
        BigDecimal erhaltenHhj,
        BigDecimal baustandaktuell,
        BigDecimal an,
        BigDecimal n1,
        BigDecimal n2,
        String bezStadtbezirke,
        @NotNull List<StadtbezirkslisteResponseDTO> stadtbezirkslisten) {

    public HhplanResponseDTO {
        stadtbezirkslisten = List.copyOf(stadtbezirkslisten);
    }
}
