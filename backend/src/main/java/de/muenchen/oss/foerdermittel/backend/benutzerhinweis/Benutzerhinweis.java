package de.muenchen.oss.foerdermittel.backend.benutzerhinweis;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a Benutzerhinweis.
 * <p>
 * The entity's attributes are mapped to the corresponding database columns.
 * </p>
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "benutzerhinweise")
public class Benutzerhinweis implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // ========= //
    // Variables //
    // ========= //

    @Column(nullable = false)
    @Id
    @NotNull @Size(min = 1, max = 30) private String viewId;

    @Column(nullable = false)
    @NotNull @Size(max = 4000) private String funktionsbeschreibung;

    @Column(nullable = false)
    @NotNull @Size(max = 4000) private String bedienung;

    @Column(nullable = false)
    @NotNull @Size(max = 4000) private String pruefungVorgaben;

}
