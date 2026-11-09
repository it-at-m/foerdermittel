package de.muenchen.oss.foerdermittel.backend.antrag.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.muenchen.oss.foerdermittel.backend.antrag.Antragstyp;
import jakarta.validation.constraints.AssertTrue;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

/// Shared plausibility rules of [AntragCreateDTO] and [AntragUpdateDTO].
public interface AntragDTO {

    LocalDate antragsdatum();

    Antragstyp antragstyp();

    LocalDate unbeddat();

    LocalDate unbedja();

    LocalDate unbedbis();

    BigDecimal asuZ();

    BigDecimal asuD();

    BigDecimal asuK();

    @JsonIgnore
    @AssertTrue(message = "antragstyp U requires unbeddat") default boolean isPflichtdatumGesetzt() {
        return antragstyp() != Antragstyp.U || unbeddat() != null;
    }

    @JsonIgnore
    @AssertTrue(message = "antragsdatum is required for all types except U") default boolean isAntragsdatumGesetzt() {
        return antragstyp() == Antragstyp.U || antragsdatum() != null;
    }

    @JsonIgnore
    @AssertTrue(message = "antragsdatum requires at least one of asuZ, asuD or asuK to be greater than 0") default boolean isBetragBeantragt() {
        return antragsdatum() == null || Stream.of(asuZ(), asuD(), asuK())
                .anyMatch(betrag -> betrag != null && betrag.signum() > 0);
    }

    @JsonIgnore
    @AssertTrue(message = "unbedja requires unbeddat and must not be before it, unbedbis requires unbedja and must not be before it") default boolean isUnbedenklichkeitKonsistent() {
        final boolean unbedjaValid = unbedja() == null || unbeddat() != null && !unbedja().isBefore(unbeddat());
        final boolean unbedbisValid = unbedbis() == null || unbedja() != null && !unbedbis().isBefore(unbedja());
        return unbedjaValid && unbedbisValid;
    }
}
