package de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SiedlungsgebietFormContextDTO(@NotNull List<Integer> siedlungsgebiete) {
    public SiedlungsgebietFormContextDTO {
        siedlungsgebiete = List.copyOf(siedlungsgebiete);
    }
}
