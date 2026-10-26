package de.muenchen.oss.foerdermittel.backend.projekt;

import de.muenchen.oss.foerdermittel.backend.bauprogramm.Bauprogramm;
import de.muenchen.oss.foerdermittel.backend.foerderbereich.Foerderbereich;
import de.muenchen.oss.foerdermittel.backend.krankenhaus.Krankenhaus;
import de.muenchen.oss.foerdermittel.backend.kurzbezeichnung.Kurzbezeichnung;
import de.muenchen.oss.foerdermittel.backend.siedlungsgebiet.Siedlungsgebiet;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import de.muenchen.oss.foerdermittel.backend.unterabschnitt.Unterabschnitt;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projekte")
public class Projekt implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "projnr", nullable = false)
    @NotNull @Size(min = 7, max = 7) private String projnr; // angepasst von projektnr auf projnr, da im Mapper so verwendet

    @Column(name = "jahr", nullable = false)
    @NotNull private String jahr;

    @Column(name = "projart", nullable = false)
    @Size(min = 1, max = 50) private String projart;

    @Column(name = "refinanzierbar", nullable = false)
    private Boolean refinanzierbar;

    @Column(name = "kauf", nullable = false)
    private Boolean kauf;

    @Column(name = "kripplatz", nullable = false, precision = 10)
    private BigDecimal kripplatz;

    @Column(name = "kigaplatz", nullable = false, precision = 10)
    private BigDecimal kigaplatz;

    @Column(name = "hortplatz", nullable = false, precision = 10)
    private BigDecimal hortplatz;

    @Column(name = "notizen", nullable = false)
    @Size(min = 1, max = 100) private String notizen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "fob_fb",
            referencedColumnName = "fb",
            nullable = false
    )
    private Foerderbereich foerderbereich;

    @Column(name = "lfdnr1", length = 1)
    private String lfdnr1;

    @Column(name = "lfdnr2", length = 2)
    private String lfdnr2;

    @Column(name = "pname", nullable = false)
    @NotNull @Size(min = 1, max = 100) private String pname;

    @Column(name = "pstrasse", nullable = false)
    @NotNull @Size(min = 1, max = 100) private String pstrasse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "bez_stadtbezirk",
            referencedColumnName = "stadtbezirk",
            nullable = false
    )
    private Stadtbezirk stadtbezirk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "uas_ua",
            referencedColumnName = "ua",
            nullable = false
    )
    private Unterabschnitt unterabschnitt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "kur_kurzbez",
            referencedColumnName = "kurzbez",
            nullable = false
    )
    private Kurzbezeichnung kurzbezeichnung;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sgt_siedlungsgebiet",
            referencedColumnName = "siedlungsgebiet",
            nullable = false
    )
    private Siedlungsgebiet siedlungsgebiet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "bpg_bauprogramm",
            referencedColumnName = "bauprogramm",
            nullable = false
    )
    private Bauprogramm bauprogramm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "krn_krhname",
            referencedColumnName = "krhname",
            nullable = false
    )
    private Krankenhaus krankenhaus;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "krisofp", columnDefinition = "projekte_krisofp")
    private Krisofp krisofp;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "krhzweck", columnDefinition = "projekte_krhzweck")
    private Krhzweck krhzweck;

    @Column(name = "anlagevon", nullable = false, length = 30)
    private String anlagevon;
}
