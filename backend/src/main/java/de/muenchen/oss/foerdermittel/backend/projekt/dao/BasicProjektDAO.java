package de.muenchen.oss.foerdermittel.backend.projekt.dao;

import jakarta.validation.constraints.NotNull;

public record BasicProjektDAO(@NotNull String projnr, @NotNull String pname, @NotNull String pstrasse) {
}
