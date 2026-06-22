package de.muenchen.oss.foerdermittel.backend.bauprogramm.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BauprogrammFormContextDTO(@NotNull List<Integer> bauprogramme) {
}
