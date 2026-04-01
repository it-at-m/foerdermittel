package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "fp_hhplan", indexes = {@Index(name = "fp_hhplan_pro_projnr_idx",
        columnList = "pro_projnr")})
public class HaushaltsPlan {
    @EmbeddedId
    private HaushaltsPlanId id;

    @MapsId("hhjJahr")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hhj_jahr", nullable = false)
    private HaushaltsJahr hhjJahr;

    @MapsId("proProjnr")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pro_projnr", nullable = false)
    private Projekt proProjnr;

    @Size(max = 100)
    @Column(name = "p_pstrasse", length = 100)
    private String pPstrasse;

    @Size(max = 100)
    @Column(name = "p_pname", length = 100)
    private String pPname;

    @Column(name = "p_fob_fb", precision = 2)
    private BigDecimal pFobFb;

    @Column(name = "p_bez_stadtbezirk", precision = 2)
    private BigDecimal pBezStadtbezirk;

    @Size(max = 3)
    @Column(name = "p_lna_kurzbez", length = 3)
    private String pLnaKurzbez;

    @Column(name = "p_vndat")
    private LocalDate pVndat;

    @Column(name = "mitteleinplanung", precision = 12)
    private BigDecimal mitteleinplanung;

    @Column(name = "bewilligung_voj", precision = 12)
    private BigDecimal bewilligungVoj;

    @Column(name = "bewilligung_hhj", precision = 12)
    private BigDecimal bewilligungHhj;

    @Column(name = "erhalten_voj", precision = 12)
    private BigDecimal erhaltenVoj;

    @Column(name = "erhalten_hhj", precision = 12)
    private BigDecimal erhaltenHhj;

    @Column(name = "baustandaktuell", precision = 12, scale = 2)
    private BigDecimal baustandaktuell;

    @Column(name = "an", precision = 12)
    private BigDecimal an;

    @Column(name = "n1", precision = 12)
    private BigDecimal n1;

    @Column(name = "n2", precision = 12)
    private BigDecimal n2;

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

    @Size(max = 15)
    @NotNull
    @ColumnDefault("'000000000000000'")
    @Column(name = "fipo", nullable = false, length = 15)
    private String fipo;


}