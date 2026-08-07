package de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record KurzbezeichnungCreateDTO(@NotBlank @Size(min = 1, max = 3) @Pattern(regexp = "^[A-Z0-9]{1,3}$") String kurzbez,
        @NotBlank @Size(min = 1, max = 200) String bezeichnung) {
}
