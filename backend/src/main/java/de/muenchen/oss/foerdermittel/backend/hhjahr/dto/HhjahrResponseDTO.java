package de.muenchen.oss.foerdermittel.backend.hhjahr.dto;

import de.muenchen.oss.foerdermittel.backend.hhplan.dto.HhplanResponseDTO;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record HhjahrResponseDTO(
        @NotNull String id,
        @NotNull BigDecimal hhjahr,
        @NotNull String hhbezeichnung,
        @NotNull String notizen,
        @NotNull List<HhplanResponseDTO> assignedProProjnr) {

    public HhjahrResponseDTO {
        assignedProProjnr = List.copyOf(assignedProProjnr);
    }
}