package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "fp_jahresstatistik1")
public class Jahresstatistik1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jss_jahr", nullable = false)
    private Jahresstatistik jssJahr;

    @Size(max = 60)
    @NotNull
    @Column(name = "foerderbereich", nullable = false, length = 60)
    private String foerderbereich;

    @NotNull
    @Column(name = "fb", nullable = false, precision = 2)
    private BigDecimal fb;

    @NotNull
    @Column(name = "gruppe", nullable = false, precision = 1)
    private BigDecimal gruppe;

    @Column(name = "gesamtkosten", precision = 12, scale = 2)
    private BigDecimal gesamtkosten;

    @Column(name = "zuwendungsfaehig", precision = 12, scale = 2)
    private BigDecimal zuwendungsfaehig;

    @Column(name = "b_zuschuss", precision = 12, scale = 2)
    private BigDecimal bZuschuss;

    @Column(name = "b_darlehen", precision = 12, scale = 2)
    private BigDecimal bDarlehen;

    @Column(name = "b_konnexitaet", precision = 12, scale = 2)
    private BigDecimal bKonnexitaet;

    @Column(name = "e_zuschuss", precision = 12, scale = 2)
    private BigDecimal eZuschuss;

    @Column(name = "e_darlehen", precision = 12, scale = 2)
    private BigDecimal eDarlehen;

    @Column(name = "e_konnexitaet", precision = 12, scale = 2)
    private BigDecimal eKonnexitaet;


}