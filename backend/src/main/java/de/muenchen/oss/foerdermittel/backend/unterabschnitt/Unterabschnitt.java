package de.muenchen.oss.foerdermittel.backend.unterabschnitt;

import de.muenchen.oss.foerdermittel.backend.hauptabschnitt.Hauptabschnitt;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a Unterabschnitt.
 * <p>
 * The entity's attributes are mapped to the corresponding database columns.
 * </p>
 */
@SuppressWarnings("PMD.LinguisticNaming")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "unterabschnitte")
public class Unterabschnitt implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // ========= //
    // Variables //
    // ========= //

    @Column(nullable = false)
    @Id
    @NotNull @Size(min = 1, max = 2) @Pattern(regexp = "^[a-zA-Z0-9]+$") private String ua;

    @Column(nullable = false)
    @NotNull @Size(min = 1, max = 200) private String bezeichnung;

    @JoinColumn(name = "has_ha", nullable = false)
    @ManyToOne(optional = false)
    @NotNull private Hauptabschnitt hasHa;

}
