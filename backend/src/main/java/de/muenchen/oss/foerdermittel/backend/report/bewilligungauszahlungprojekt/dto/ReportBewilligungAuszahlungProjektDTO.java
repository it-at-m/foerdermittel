package de.muenchen.oss.foerdermittel.backend.report.bewilligungauszahlungprojekt.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ReportBewilligungAuszahlungProjektDTO(@NotBlank @Pattern(regexp = "\\S") String projnr, boolean erfolgt) {
}
