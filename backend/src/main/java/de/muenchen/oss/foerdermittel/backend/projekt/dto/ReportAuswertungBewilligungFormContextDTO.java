package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import jakarta.validation.constraints.NotNull;

public record ReportAuswertungBewilligungFormContextDTO(
        @NotNull String projnr,
        String pname,
        String pstrasse) {
}
