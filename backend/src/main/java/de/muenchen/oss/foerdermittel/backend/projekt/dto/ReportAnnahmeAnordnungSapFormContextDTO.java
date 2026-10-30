package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import jakarta.validation.constraints.NotNull;

public record ReportAnnahmeAnordnungSapFormContextDTO (
        @NotNull String projnr,
        @NotNull String pstrasse,
        @NotNull String pname){
}
