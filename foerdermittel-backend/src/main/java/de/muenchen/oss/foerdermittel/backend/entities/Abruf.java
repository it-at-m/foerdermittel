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
@Table(name = "fp_abrufe", indexes = {
        @Index(name = "fp_abrufe_pro_projnr_idx",
                columnList = "pro_projnr"),
        @Index(name = "fp_abrufe_bwi_id_idx",
                columnList = "bwi_id"),
        @Index(name = "fp_abrufe_ref_refnr_idx",
                columnList = "ref_refnr")})
public class Abruf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pro_projnr", nullable = false)
    private Projekt proProjnr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bwi_id")
    private Bewilligung bwi;

    @Size(max = 1)
    @Column(name = "vnabr", length = 1)
    private String vnabr;

    @NotNull
    @Column(name = "abruf_z", nullable = false, precision = 10)
    private BigDecimal abrufZ;

    @NotNull
    @Column(name = "abruf_d", nullable = false, precision = 10)
    private BigDecimal abrufD;

    @NotNull
    @Column(name = "abruf_k", nullable = false, precision = 10)
    private BigDecimal abrufK;

    @Column(name = "abruf_datum")
    private LocalDate abrufDatum;

    @NotNull
    @Column(name = "erh_z", nullable = false, precision = 10)
    private BigDecimal erhZ;

    @NotNull
    @Column(name = "erh_d", nullable = false, precision = 10)
    private BigDecimal erhD;

    @NotNull
    @Column(name = "erh_k", nullable = false, precision = 10)
    private BigDecimal erhK;

    @Column(name = "erh_datum")
    private LocalDate erhDatum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_refnr")
    private Referat refRefnr;

    @Column(name = "sapabrufauftragsnr", precision = 8)
    private BigDecimal sapabrufauftragsnr;

    @Column(name = "sapfakturanr", precision = 10)
    private BigDecimal sapfakturanr;

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


}