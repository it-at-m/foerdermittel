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
@Table(name = "fp_bewilligungen", indexes = {
        @Index(name = "fp_bewilligungen_pro_projnr_idx",
                columnList = "pro_projnr"),
        @Index(name = "fp_bewilligungen_ant_id_idx",
                columnList = "ant_id")})
public class Bewilligung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pro_projnr", nullable = false)
    private Projekt proProjnr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ant_id")
    private Antrag ant;

    @Column(name = "bdatum")
    private LocalDate bdatum;

    @NotNull
    @Column(name = "afsatz", nullable = false, precision = 8, scale = 2)
    private BigDecimal afsatz;

    @NotNull
    @Column(name = "bfsatz", nullable = false, precision = 8, scale = 2)
    private BigDecimal bfsatz;

    @NotNull
    @Column(name = "bzwfkosten", nullable = false, precision = 10)
    private BigDecimal bzwfkosten;

    @Column(name = "bzuwendung_z", precision = 10)
    private BigDecimal bzuwendungZ;

    @Column(name = "bzuwendung_d", precision = 10)
    private BigDecimal bzuwendungD;

    @Column(name = "bzuwendung_k", precision = 10)
    private BigDecimal bzuwendungK;

    @Size(max = 1)
    @Column(name = "bzuwart", length = 1)
    private String bzuwart;

    @Size(max = 20)
    @Column(name = "baktenzeichen", length = 20)
    private String baktenzeichen;

    @NotNull
    @Column(name = "geszuwendungen", nullable = false, precision = 10)
    private BigDecimal geszuwendungen;

    @NotNull
    @Column(name = "geskonnex", nullable = false, precision = 10)
    private BigDecimal geskonnex;

    @Size(max = 1)
    @Column(name = "krw", length = 1)
    private String krw;

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

    @OneToMany(mappedBy = "bwi")
    private Set<Abruf> abrufe = new LinkedHashSet<>();


}