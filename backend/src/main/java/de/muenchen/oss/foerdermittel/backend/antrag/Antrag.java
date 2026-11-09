package de.muenchen.oss.foerdermittel.backend.antrag;

import de.muenchen.oss.foerdermittel.backend.common.AuditableEntity;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Table(name = "antraege")
@SuppressWarnings("PMD.TooManyFields") // mirrors the columns of table antraege
public class Antrag extends AuditableEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    // ========= //
    // Variables //
    // ========= //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /// Projektnummer
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "pro_projnr",
            referencedColumnName = "projnr",
            nullable = false
    )
    @NotNull private Projekt projekt;

    /// Antragsdatum
    @Column(name = "antragsdatum")
    private LocalDate antragsdatum;

    /// Antragstyp
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "antragstyp", nullable = false, columnDefinition = "antraege_antragstyp")
    @NotNull private Antragstyp antragstyp;

    /// Beantragte Gesamtkosten
    @Column(name = "geskosten", nullable = false)
    @NotNull private BigDecimal geskosten;

    /// Beantragte zuwendungsfähige Kosten
    @Column(name = "zwfkosten", nullable = false)
    @NotNull private BigDecimal zwfkosten;

    /// Antrag auf vorzeitigen Baubeginn gestellt
    @Column(name = "vorzbeg", nullable = false)
    @NotNull private Boolean vorzbeg;

    /// Genehmigt mit Datum vom
    @Column(name = "vbdatum")
    private LocalDate vbdatum;

    /// Antrag auf Unbedenklichkeit gestellt am
    @Column(name = "unbeddat")
    private LocalDate unbeddat;

    /// Antrag auf Unbedenklichkeit erteilt am
    @Column(name = "unbedja")
    private LocalDate unbedja;

    /// Antrag auf Unbedenklichkeit befristet bis
    @Column(name = "unbedbis")
    private LocalDate unbedbis;

    /// Beantragter Zuschuss (Jahr)
    @Column(name = "a_su_z", nullable = false)
    @NotNull private BigDecimal asuZ;

    /// Beantragtes Darlehen (Jahr)
    @Column(name = "a_su_d", nullable = false)
    @NotNull private BigDecimal asuD;

    /// Beantragte Konnexität (Jahr)
    @Column(name = "a_su_k", nullable = false)
    @NotNull private BigDecimal asuK;

    /// Voraussichtlicher Gesamtzuschuss
    @Column(name = "b_vor_su_z", nullable = false)
    @NotNull private BigDecimal bvorSuZ;

    /// Voraussichtliches Gesamtdarlehen
    @Column(name = "b_vor_su_d", nullable = false)
    @NotNull private BigDecimal bvorSuD;

    /// Voraussichtliche Gesamtkonnexität
    @Column(name = "b_vor_su_k", nullable = false)
    @NotNull private BigDecimal bvorSuK;

    /// Notizen / Zustimmung vorzeitiger Baubeginn notwendig bis
    @Column(name = "notizen")
    private String notizen;

}
