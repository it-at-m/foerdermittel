package de.muenchen.oss.foerdermittel.backend.antrag.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/// Projekt for the [de.muenchen.oss.foerdermittel.backend.antrag.AntragFormContext], including
/// the existing Antraege of the types that may exist only once per Projekt.
///
/// @param projnr
/// @param pname
/// @param pstrasse
/// @param fobFb
/// @param erstantragId id of the existing Antrag of type E, null if there is none
/// @param unbedenklichkeitsantragId id of the existing Antrag of type U, null if there is none
public record AntragProjektFormContextDTO(
        @NotNull String projnr,
        @NotNull String pname,
        @NotNull String pstrasse,
        @NotNull BigDecimal fobFb,
        Long erstantragId,
        Long unbedenklichkeitsantragId) {
}
