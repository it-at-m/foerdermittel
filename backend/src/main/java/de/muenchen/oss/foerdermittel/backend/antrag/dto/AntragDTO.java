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
    @AssertTrue(message = "antragstyp U requires unbeddat") default boolean isUnbeddatSetForTypeU() {
        return antragstyp() != Antragstyp.U || unbeddat() != null;
    }

    @JsonIgnore
    @AssertTrue(message = "antragsdatum is required for all types except U") default boolean isAntragsdatumSet() {
        return antragstyp() == Antragstyp.U || antragsdatum() != null;
    }

    @JsonIgnore
    @AssertTrue(message = "antragstyp U must not have an antragsdatum") default boolean isAntragsdatumEmptyForTypeU() {
        return antragstyp() != Antragstyp.U || antragsdatum() == null;
    }

    @JsonIgnore
    @AssertTrue(message = "all types except U require at least one of asuZ, asuD or asuK to be greater than 0") default boolean isAmountRequested() {
        return antragstyp() == Antragstyp.U || Stream.of(asuZ(), asuD(), asuK())
                .anyMatch(betrag -> betrag != null && betrag.signum() > 0);
    }

    @JsonIgnore
    @AssertTrue(message = "unbeddat, unbedja and unbedbis are only allowed for antragstyp U") default boolean isUnbedenklichkeitOnlyForTypeU() {
        return antragstyp() == Antragstyp.U || unbeddat() == null && unbedja() == null && unbedbis() == null;
    }

    @JsonIgnore
    @AssertTrue(message = "unbedbis requires unbedja") default boolean isUnbedjaSetForUnbedbis() {
        return unbedbis() == null || unbedja() != null;
    }
}
