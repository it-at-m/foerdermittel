package de.muenchen.oss.foerdermittel.backend.hhjahr.dto;

import de.muenchen.oss.foerdermittel.backend.hhplan.dto.HhplanResponseDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record HhjahrUpdateDTO(
        @NotNull @Size(max = 200) String hhbezeichnung,
        String notizen) {
}
