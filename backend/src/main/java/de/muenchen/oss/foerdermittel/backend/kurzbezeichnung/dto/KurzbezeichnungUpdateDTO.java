package de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record KurzbezeichnungUpdateDTO(@NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
