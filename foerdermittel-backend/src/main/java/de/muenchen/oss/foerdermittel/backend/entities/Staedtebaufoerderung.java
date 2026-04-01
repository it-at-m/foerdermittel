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
@Table(name = "fp_staedtebaufoerderungen")
public class Staedtebaufoerderung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "bdat")
    private LocalDate bdat;

    @Column(name = "bnr", precision = 4)
    private BigDecimal bnr;

    @Column(name = "bjahr", precision = 4)
    private BigDecimal bjahr;

    @Column(name = "betrag", precision = 10)
    private BigDecimal betrag;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "soz", nullable = false)
    private Boolean soz;

    @Size(max = 40)
    @Column(name = "azbank", length = 40)
    private String azbank;

    @Column(name = "antrnr", precision = 3)
    private BigDecimal antrnr;

    @Column(name = "antrjahr", precision = 4)
    private BigDecimal antrjahr;

    @Size(max = 6)
    @Column(name = "azstk", length = 6)
    private String azstk;

    @Column(name = "ausbet", precision = 10)
    private BigDecimal ausbet;

    @Column(name = "erhdat")
    private LocalDate erhdat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tra_kurzform")
    private Traeger traKurzform;

    @Size(max = 15)
    @Column(name = "hst", length = 15)
    private String hst;

    @Size(max = 5)
    @Column(name = "hulnr", length = 5)
    private String hulnr;

    @Column(name = "huljahr", precision = 4)
    private BigDecimal huljahr;

    @Column(name = "notizen", length = Integer.MAX_VALUE)
    private String notizen;

    @Size(max = 60)
    @Column(name = "projektname", length = 60)
    private String projektname;

    @Column(name = "restlos")
    private LocalDate restlos;

    @Column(name = "zahlanz")
    private LocalDate zahlanz;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "schuldurk", nullable = false)
    private Boolean schuldurk;

    @Column(name = "alt_lfdnr", precision = 10)
    private BigDecimal altLfdnr;


}