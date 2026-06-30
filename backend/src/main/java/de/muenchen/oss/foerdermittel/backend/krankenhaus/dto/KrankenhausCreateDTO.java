package de.muenchen.oss.foerdermittel.backend.krankenhaus.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public record KrankenhausCreateDTO(@NotNull @Length(min = 1, max = 1) @Pattern(regexp = "^[A-Z]+$") String krhname, @NotNull @Size(min = 1, max = 200) String bezeichnung) {
}

