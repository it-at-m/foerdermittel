package de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.dto;

import com.google.errorprone.annotations.NoAllocation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record KurzbezeichnungCreateDTO(@NoAllocation @Size(min = 1, max = 3) @Pattern(regexp = "^[A-Z]{1,3}$") String kurzbez,
        @NotNull @Size(min = 1, max = 200) String bezeichnung) {
}
