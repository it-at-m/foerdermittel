package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import jakarta.validation.constraints.NotNull;

public record ProjektFormContextDTO(
        @NotNull String projnr ,
        String pstrasse,
        String pname,
        String foerderbereich) {
}
