package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "fp_projektistkosten", indexes = {@Index(name = "fp_projektistkosten_pro_projnr_idx",
        columnList = "pro_projnr")})
public class ProjektIstKosten {
    @EmbeddedId
    private ProjektIstKostenId id;

    @MapsId("proProjnr")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pro_projnr", nullable = false)
    private Projekt proProjnr;

    @NotNull
    @Column(name = "istkosten", nullable = false, precision = 12)
    private BigDecimal istkosten;


}