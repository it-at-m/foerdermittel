package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode
@Embeddable
public class StadtbezirkslisteId implements Serializable {
    private static final long serialVersionUID = 4017955240686531929L;
    @Size(max = 3)
    @NotNull
    @Column(name = "lna_kurzbez", nullable = false, length = 3)
    private String lnaKurzbez;

    @NotNull
    @Column(name = "bez_stadtbezirk", nullable = false, precision = 2)
    private BigDecimal bezStadtbezirk;


}