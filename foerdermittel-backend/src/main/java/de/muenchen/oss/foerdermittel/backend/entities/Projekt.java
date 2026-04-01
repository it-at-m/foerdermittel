package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "fp_projekte", indexes = {
        @Index(name = "fp_projekte_fob_fb_idx",
                columnList = "fob_fb"),
        @Index(name = "fp_projekte_kur_kurzbez_idx",
                columnList = "kur_kurzbez"),
        @Index(name = "fp_projekte_uas_ua_idx",
                columnList = "uas_ua"),
        @Index(name = "fp_projekte_ble_bauleitung_idx",
                columnList = "ble_bauleitung"),
        @Index(name = "fp_projekte_bez_stadtbezirk_idx",
                columnList = "bez_stadtbezirk"),
        @Index(name = "fp_projekte_krn_krhname_idx",
                columnList = "krn_krhname"),
        @Index(name = "fp_projekte_aenderungsdatum_idx",
                columnList = "aenderungsdatum"),
        @Index(name = "fp_projekte_bpg_bauprogramm_idx",
                columnList = "bpg_bauprogramm"),
        @Index(name = "fp_projekte_sgt_siedlungsgebiet_idx",
                columnList = "sgt_siedlungsgebiet")})
public class Projekt {
    @Id
    @Size(max = 7)
    @Column(name = "projnr", nullable = false, length = 7)
    private String projnr;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fob_fb", nullable = false)
    private Foerderbereich fobFb;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kur_kurzbez", nullable = false)
    private Kurzbezeichnung kurKurzbez;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uas_ua", nullable = false)
    private Unterabschnitt uasUa;

    @Size(max = 2)
    @NotNull
    @Column(name = "jahr", nullable = false, length = 2)
    private String jahr;

    @Size(max = 1)
    @NotNull
    @Column(name = "lfdnr1", nullable = false, length = 1)
    private String lfdnr1;

    @Size(max = 2)
    @NotNull
    @Column(name = "lfdnr2", nullable = false, length = 2)
    private String lfdnr2;

    @Size(max = 100)
    @Column(name = "pname", length = 100)
    private String pname;

    @Size(max = 100)
    @Column(name = "pstrasse", length = 100)
    private String pstrasse;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "kauf", nullable = false)
    private Boolean kauf;

    @Size(max = 50)
    @Column(name = "projart", length = 50)
    private String projart;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "refinanzierbar", nullable = false)
    private Boolean refinanzierbar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ble_bauleitung")
    private Bauleitung bleBauleitung;

    @Size(max = 30)
    @Column(name = "bauleitungkontakt", length = 30)
    private String bauleitungkontakt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bez_stadtbezirk")
    private Stadtbezirk bezStadtbezirk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "krn_krhname")
    private Krankenhaus krnKrhname;

    @Size(max = 1)
    @Column(name = "krhzweck", length = 1)
    private String krhzweck;

    @Size(max = 1)
    @Column(name = "krisofp", length = 1)
    private String krisofp;

    @Column(name = "kripplatz", precision = 10)
    private BigDecimal kripplatz;

    @Column(name = "kigaplatz", precision = 10)
    private BigDecimal kigaplatz;

    @Column(name = "hortplatz", precision = 10)
    private BigDecimal hortplatz;

    @Column(name = "vndat")
    private LocalDate vndat;

    @Column(name = "vnkosten", precision = 12, scale = 2)
    private BigDecimal vnkosten;

    @Column(name = "vnzwfkosten", precision = 12, scale = 2)
    private BigDecimal vnzwfkosten;

    @Column(name = "vnpruefdat")
    private LocalDate vnpruefdat;

    @Column(name = "vnrueck_z", precision = 12, scale = 2)
    private BigDecimal vnrueckZ;

    @Column(name = "vnnachfoerderung", precision = 12, scale = 2)
    private BigDecimal vnnachfoerderung;

    @Column(name = "vnpruefzwf", precision = 12, scale = 2)
    private BigDecimal vnpruefzwf;

    @Column(name = "vnschlusszwf", precision = 12, scale = 2)
    private BigDecimal vnschlusszwf;

    @Column(name = "vnschlussbew")
    private LocalDate vnschlussbew;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "sap", nullable = false)
    private Boolean sap;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "sapstatauf", nullable = false)
    private Boolean sapstatauf;

    @Column(name = "sapmatnr", precision = 8)
    private BigDecimal sapmatnr;

    @Column(name = "sapwertnr", precision = 8)
    private BigDecimal sapwertnr;

    @Column(name = "sapjahrwert")
    private LocalDate sapjahrwert;

    @Column(name = "sapanlagennr", precision = 8)
    private BigDecimal sapanlagennr;

    @Column(name = "zinsen", precision = 10)
    private BigDecimal zinsen;

    @Column(name = "zinsdatum")
    private LocalDate zinsdatum;

    @Size(max = 15)
    @Column(name = "fipo", length = 15)
    private String fipo;

    @Size(max = 4)
    @Column(name = "buchungskreis", length = 4)
    private String buchungskreis;

    @Size(max = 6)
    @Column(name = "sachkonto", length = 6)
    private String sachkonto;

    @Size(max = 15)
    @Column(name = "fipo_k", length = 15)
    private String fipoK;

    @Size(max = 4)
    @Column(name = "buchungskreis_k", length = 4)
    private String buchungskreisK;

    @Size(max = 6)
    @Column(name = "sachkonto_k", length = 6)
    private String sachkontoK;

    @Size(max = 2)
    @Column(name = "psbaubuch", length = 2)
    private String psbaubuch;

    @Size(max = 2)
    @Column(name = "psbauref", length = 2)
    private String psbauref;

    @Size(max = 6)
    @Column(name = "psbaunr", length = 6)
    private String psbaunr;

    @Column(name = "notizen", length = Integer.MAX_VALUE)
    private String notizen;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "altdaten", nullable = false)
    private Boolean altdaten;

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

    @Column(name = "vngesamtzuwendung", precision = 12, scale = 2)
    private BigDecimal vngesamtzuwendung;

    @Size(max = 12)
    @Column(name = "sapinnenauftrag", length = 12)
    private String sapinnenauftrag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bpg_bauprogramm")
    private Bauprogram bpgBauprogramm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sgt_siedlungsgebiet")
    private Siedlungsgebiet sgtSiedlungsgebiet;

    @Column(name = "bauende")
    private LocalDate bauende;

    @Size(max = 1)
    @Column(name = "baubeendet", length = 1)
    private String baubeendet;

    @Column(name = "bauvergabe1")
    private LocalDate bauvergabe1;

    @Column(name = "baubeginn")
    private LocalDate baubeginn;

    @Column(name = "baumitteilung")
    private LocalDate baumitteilung;

    @Column(name = "kreditnummer", precision = 8)
    private BigDecimal kreditnummer;

    @Size(max = 50)
    @Column(name = "stadtanleihe", length = 50)
    private String stadtanleihe;

    @Column(name = "anleihenennwert", precision = 9)
    private BigDecimal anleihenennwert;

    @Column(name = "anleihejahrvon", precision = 4)
    private BigDecimal anleihejahrvon;

    @Column(name = "anleihejahrbis", precision = 4)
    private BigDecimal anleihejahrbis;

    @OneToMany(mappedBy = "proProjnr")
    private Set<Abruf> abrufe = new LinkedHashSet<>();

    @OneToMany(mappedBy = "proProjnr")
    private Set<Antrag> antraege = new LinkedHashSet<>();

    @OneToMany(mappedBy = "proProjnr")
    private Set<Archiv> archive = new LinkedHashSet<>();

    @OneToMany(mappedBy = "proProjnr")
    private Set<Bewilligung> bewilligungen = new LinkedHashSet<>();

    @OneToMany(mappedBy = "proProjnr")
    private Set<HaushaltsPlan> haushaltsPlaene = new LinkedHashSet<>();

    @OneToMany(mappedBy = "proProjnr")
    private Set<ProjektIstKosten> projektIstKosten = new LinkedHashSet<>();

    @OneToMany(mappedBy = "proProjnr")
    private Set<ProjektTermin> projektTermine = new LinkedHashSet<>();


}