package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "fp_euinformationen")
public class EuInformationen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "jahr", nullable = false, precision = 4)
    private BigDecimal jahr;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pub_kurzform", nullable = false)
    private Publikation pubKurzform;

    @Size(max = 1)
    @Column(name = "hefta", length = 1)
    private String hefta;

    @NotNull
    @Column(name = "nummer", nullable = false, precision = 3)
    private BigDecimal nummer;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "wichtig", nullable = false)
    private Boolean wichtig;

    @Size(max = 3)
    @Column(name = "seitennr", length = 3)
    private String seitennr;

    @Size(max = 50)
    @Column(name = "stichwort", length = 50)
    private String stichwort;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "rawi", nullable = false)
    private Boolean rawi;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "schulref", nullable = false)
    private Boolean schulref;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "sozref_r_5", nullable = false)
    private Boolean sozrefR5;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "rgu_11", nullable = false)
    private Boolean rgu11;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "rgu_cs", nullable = false)
    private Boolean rguCs;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "krh", nullable = false)
    private Boolean krh;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "afa", nullable = false)
    private Boolean afa;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "swm", nullable = false)
    private Boolean swm;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "kulturref", nullable = false)
    private Boolean kulturref;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "bauref", nullable = false)
    private Boolean bauref;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "planref", nullable = false)
    private Boolean planref;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "direktorium", nullable = false)
    private Boolean direktorium;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "por", nullable = false)
    private Boolean por;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "kvr", nullable = false)
    private Boolean kvr;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "kommref", nullable = false)
    private Boolean kommref;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "sew", nullable = false)
    private Boolean sew;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "stk", nullable = false)
    private Boolean stk;

    @Size(max = 200)
    @NotNull
    @Column(name = "inhalt", nullable = false, length = 200)
    private String inhalt;

    @Column(name = "infodat")
    private LocalDate infodat;


}