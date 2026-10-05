package de.muenchen.oss.foerdermittel.backend.projekt.dto;

import jakarta.validation.constraints.NotNull;

public record ReportProjektuebersichtFormContextDTO(
        @NotNull String projnr,
        @NotNull String pname,
        @NotNull String pstrasse) {
}
