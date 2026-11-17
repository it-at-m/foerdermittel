package de.muenchen.oss.foerdermittel.backend.hhjahr.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record HhjahrUpdateDTO(
        @NotNull @Size(max = 200) String hhbezeichnung,
        @Size(max = 4000) String notizen) {
}
