package de.muenchen.oss.foerdermittel.backend.istkosten;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class IstkostenPrimaryKey implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank @Column(name = "pro_projnr")
    private String projnr;

    @NotNull @Min(1970) @Max(2100) @Column(name = "jahr")
    private BigDecimal jahr;

    @NotNull @Min(1) @Max(12) @Column(name = "monat")
    private BigDecimal monat;

    public static final String PRIMARYKEY_REGEX = "^(?<id>.+?)-(?<jahr>19[7-9]\\d|20\\d{2}|2100)-(?<monat>[1-9]|1[0-2])$";
    private static final Pattern ID_PATTERN = Pattern.compile(
            PRIMARYKEY_REGEX);

    public static IstkostenPrimaryKey toPrimaryKey(final String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }

        final Matcher matcher = ID_PATTERN.matcher(id);

        if (!matcher.matches()) {
            throwInvalidId(id);
        }

        return new IstkostenPrimaryKey(
                matcher.group("id"),
                new BigDecimal(matcher.group("jahr")),
                new BigDecimal(matcher.group("monat")));
    }

    private static void throwInvalidId(final String id) {
        throw new IllegalArgumentException("Ungültige ID: " + id);
    }

    @Override
    public String toString() {
        if (jahr == null) {
            throw new IllegalStateException("Jahr darf nicht null sein");
        }
        if (monat == null) {
            throw new IllegalStateException("Monat darf nicht null sein");
        }

        return String.format("%s-%s-%s",
                projnr,
                jahr.setScale(0, RoundingMode.UNNECESSARY).toPlainString(),
                monat.setScale(0, RoundingMode.UNNECESSARY).toPlainString());
    }

}
