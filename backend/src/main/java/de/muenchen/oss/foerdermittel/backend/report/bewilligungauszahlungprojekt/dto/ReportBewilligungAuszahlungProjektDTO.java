package de.muenchen.oss.foerdermittel.backend.report.bewilligungauszahlungprojekt.dto;

import jakarta.validation.constraints.NotBlank;

public record ReportBewilligungAuszahlungProjektDTO(@NotBlank String projnr, boolean erfolgt) {
}
