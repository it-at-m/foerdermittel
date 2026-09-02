package de.muenchen.oss.foerdermittel.backend.projekttermin;

import de.muenchen.oss.foerdermittel.backend.projekt.Projekt;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a Termine.
 * <p>
 * The entity's attributes are mapped to the corresponding database columns.
 * </p>
 */
@SuppressWarnings("PMD.LinguisticNaming")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "projekttermine")
public class Projekttermin implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // ========= //
    // Variables //
    // ========= //

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "pro_projnr",
            referencedColumnName = "projnr",
            nullable = false
    )
    @NotNull private Projekt projekt;

    @Column(name = "termin", nullable = false)
    @NotNull private LocalDate termin;

    @Column(name = "zustaendig")
    @Size(min = 1, max = 30) private String zustaendig;

    @Column(name = "ueberwachung")
    private Boolean ueberwachung;

    @Column(name = "telefon")
    @Size(min = 1, max = 30) private String telefon;

    @Column(name = "notizen")
    private String notizen;

}
