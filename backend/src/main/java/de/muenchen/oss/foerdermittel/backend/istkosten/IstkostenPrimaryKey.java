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

    @Override
    public String toString() {
        return String.format("%s-%s-%s", projnr, jahr.toPlainString(), monat.toPlainString());
    }

}
