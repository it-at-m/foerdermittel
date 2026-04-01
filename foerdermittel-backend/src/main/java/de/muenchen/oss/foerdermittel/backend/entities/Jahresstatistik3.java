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
@Table(name = "fp_jahresstatistik3")
public class Jahresstatistik3 {
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

    @Column(name = "bzuwendung_z_plus", precision = 12, scale = 2)
    private BigDecimal bzuwendungZPlus;

    @Column(name = "bzuwendung_z_minus", precision = 12, scale = 2)
    private BigDecimal bzuwendungZMinus;

    @Column(name = "bzuwendung_d_plus", precision = 12, scale = 2)
    private BigDecimal bzuwendungDPlus;

    @Column(name = "bzuwendung_d_minus", precision = 12, scale = 2)
    private BigDecimal bzuwendungDMinus;

    @Column(name = "bzuwendung_k_plus", precision = 12, scale = 2)
    private BigDecimal bzuwendungKPlus;

    @Column(name = "bzuwendung_k_minus", precision = 12, scale = 2)
    private BigDecimal bzuwendungKMinus;

    @Column(name = "erh_z", precision = 12, scale = 2)
    private BigDecimal erhZ;

    @Column(name = "erh_d", precision = 12, scale = 2)
    private BigDecimal erhD;

    @Column(name = "erh_k", precision = 12, scale = 2)
    private BigDecimal erhK;


}