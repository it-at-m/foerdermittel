package de.muenchen.oss.foerdermittel.backend.hhplan;

import de.muenchen.oss.foerdermittel.backend.common.AuditableEntity;
import de.muenchen.oss.foerdermittel.backend.hhjahr.Hhjahr;
import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/// This class represents a Hhplan.
///
/// The entity's attributes are mapped to the corresponding database columns.
///
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hhplan")
public class Hhplan extends AuditableEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // ========= //
    // Variables //
    // ========= //

    @EmbeddedId
    private HhplanPrimaryKey id;

    @Column(nullable = false)
    @NotBlank @Size(min = 0, max = 15) private String fipo;

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

    @Column(name = "baustandaktuell", precision = 12)
    private BigDecimal baustandaktuell;

    @Column(name = "an", precision = 12)
    private BigDecimal an;

    @Column(name = "n1", precision = 12)
    private BigDecimal n1;

    @Column(name = "n2", precision = 12)
    private BigDecimal n2;

    @Column(name = "notizen")
    private String notizen;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("proProjnr")
    @JoinColumn(name = "pro_projnr", referencedColumnName = "projnr", nullable = false)
    private Projekt proProjnr;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("hhjJahr")
    @JoinColumn(name = "hhj_jahr", nullable = false)
    private Hhjahr hhjJahr;
}
