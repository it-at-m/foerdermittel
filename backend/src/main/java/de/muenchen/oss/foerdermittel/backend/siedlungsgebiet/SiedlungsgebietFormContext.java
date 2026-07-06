package de.muenchen.oss.foerdermittel.backend.siedlungsgebiet;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record SiedlungsgebietFormContext(@NotNull List<BigDecimal> siedlungsgebiete) {
    public SiedlungsgebietFormContext {
        siedlungsgebiete = List.copyOf(siedlungsgebiete);
    }
}
