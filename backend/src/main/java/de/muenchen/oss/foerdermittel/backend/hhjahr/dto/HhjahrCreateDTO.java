package de.muenchen.oss.foerdermittel.backend.hhjahr.dto;

import de.muenchen.oss.foerdermittel.backend.hhplan.dto.HhplanResponseDTO;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record HhjahrCreateDTO(
        @NotNull @Min(0) @Digits(integer = 4, fraction = 0) BigDecimal hhjahr,
        @NotNull @Size(max = 200) String hhbezeichnung,
        String notizen,
        @NotNull List<HhplanResponseDTO> assignedProProjnr) {

    public HhjahrCreateDTO {
        assignedProProjnr = List.copyOf(assignedProProjnr);
    }
}
