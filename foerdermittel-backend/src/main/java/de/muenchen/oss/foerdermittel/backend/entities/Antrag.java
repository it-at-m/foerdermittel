package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "fp_antraege", indexes = {@Index(name = "fp_antraege_pro_projnr_idx",
        columnList = "pro_projnr")})
public class Antrag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pro_projnr", nullable = false)
    private Projekt proProjnr;

    @Column(name = "antragsdatum")
    private LocalDate antragsdatum;

    @Size(max = 1)
    @NotNull
    @Column(name = "antragstyp", nullable = false, length = 1)
    private String antragstyp;

    @NotNull
    @Column(name = "geskosten", nullable = false, precision = 10)
    private BigDecimal geskosten;

    @NotNull
    @Column(name = "zwfkosten", nullable = false, precision = 10)
    private BigDecimal zwfkosten;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "vorzbeg", nullable = false)
    private Boolean vorzbeg;

    @Column(name = "vbdatum")
    private LocalDate vbdatum;

    @Column(name = "unbeddat")
    private LocalDate unbeddat;

    @Column(name = "unbedja")
    private LocalDate unbedja;

    @Column(name = "unbedbis")
    private LocalDate unbedbis;

    @NotNull
    @Column(name = "a_su_z", nullable = false, precision = 10)
    private BigDecimal aSuZ;

    @NotNull
    @Column(name = "a_su_d", nullable = false, precision = 10)
    private BigDecimal aSuD;

    @NotNull
    @Column(name = "a_su_k", nullable = false, precision = 10)
    private BigDecimal aSuK;

    @NotNull
    @Column(name = "b_vor_su_z", nullable = false, precision = 10)
    private BigDecimal bVorSuZ;

    @NotNull
    @Column(name = "b_vor_su_d", nullable = false, precision = 10)
    private BigDecimal bVorSuD;

    @NotNull
    @Column(name = "b_vor_su_k", nullable = false, precision = 10)
    private BigDecimal bVorSuK;

    @Column(name = "notizen", length = Integer.MAX_VALUE)
    private String notizen;

    @NotNull
    @ColumnDefault("CURRENT_DATE")
    @Column(name = "anlagedatum", nullable = false)
    private LocalDate anlagedatum;

    @Size(max = 30)
    @NotNull
    @Column(name = "anlagevon", nullable = false, length = 30)
    private String anlagevon;

    @Column(name = "aenderungsdatum")
    private LocalDate aenderungsdatum;

    @Size(max = 30)
    @Column(name = "aenderungvon", length = 30)
    private String aenderungvon;

    @OneToMany(mappedBy = "ant")
    private Set<Bewilligung> bewilligungen = new LinkedHashSet<>();


}