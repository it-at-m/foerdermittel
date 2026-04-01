package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class ProjektIstKostenId implements Serializable {
    private static final long serialVersionUID = -4023109776197443325L;
    @Size(max = 7)
    @NotNull
    @Column(name = "pro_projnr", nullable = false, length = 7)
    private String proProjnr;

    @NotNull
    @Column(name = "jahr", nullable = false, precision = 4)
    private BigDecimal jahr;

    @NotNull
    @Column(name = "monat", nullable = false, precision = 2)
    private BigDecimal monat;


}