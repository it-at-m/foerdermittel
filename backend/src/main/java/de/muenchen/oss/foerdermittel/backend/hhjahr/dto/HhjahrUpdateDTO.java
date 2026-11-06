package de.muenchen.oss.foerdermittel.backend.hhjahr.dto;

import de.muenchen.oss.foerdermittel.backend.hhplan.dto.HhplanResponseDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record HhjahrUpdateDTO(
        @NotNull @Max(200) String hhbezeichnung,
        String notizen,
        @NotNull List<HhplanResponseDTO> assignedProProjnr) {

    public HhjahrUpdateDTO {
        assignedProProjnr = List.copyOf(assignedProProjnr);
    }
}
