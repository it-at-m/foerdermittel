package de.muenchen.oss.foerdermittel.backend.antrag.dto;

import de.muenchen.oss.foerdermittel.backend.antrag.Antragstyp;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

// CPD-OFF: same fields as AntragCreateDTO without projnr, kept separate like the other Create/Update DTO pairs
public record AntragUpdateDTO(
        @NotNull Antragstyp antragstyp,
        LocalDate antragsdatum,
        @NotNull @PositiveOrZero @Digits(integer = 10, fraction = 0) BigDecimal geskosten,
        @NotNull @PositiveOrZero @Digits(integer = 10, fraction = 0) BigDecimal zwfkosten,
        @NotNull Boolean vorzbeg,
        LocalDate vbdatum,
        LocalDate unbeddat,
        LocalDate unbedja,
        LocalDate unbedbis,
        @NotNull @PositiveOrZero @Digits(integer = 10, fraction = 0) BigDecimal asuZ,
        @NotNull @PositiveOrZero @Digits(integer = 10, fraction = 0) BigDecimal asuD,
        @NotNull @PositiveOrZero @Digits(integer = 10, fraction = 0) BigDecimal asuK,
        @NotNull @PositiveOrZero @Digits(integer = 10, fraction = 0) BigDecimal bvorSuZ,
        @NotNull @PositiveOrZero @Digits(integer = 10, fraction = 0) BigDecimal bvorSuD,
        @NotNull @PositiveOrZero @Digits(integer = 10, fraction = 0) BigDecimal bvorSuK,
        String notizen) implements AntragDTO {
}
// CPD-ON
