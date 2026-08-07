package de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record KurzbezeichnungUpdateDTO(@NotBlank @Size(min = 1, max = 200) String bezeichnung) {
}
