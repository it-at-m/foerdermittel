package de.muenchen.oss.foerdermittel.backend.report.bewilligungauszahlungprojekt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ReportBewilligungAuszahlungProjektDTO(@NotBlank @Schema(pattern = "\\S") String projnr, boolean erfolgt) {
}
