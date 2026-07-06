package de.muenchen.oss.foerdermittel.backend.bauleitung;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

import java.io.Serial;
import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a Bauleitung.
 * <p>
 * The entity's attributes are mapped to the corresponding database columns.
 * </p>
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bauleitungen")
public class Bauleitung implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // ========= //
    // Variables //
    // ========= //

    @Column(nullable = false)
    @Id
    @NotNull @Size(min = 1, max = 1) @Pattern(regexp = "^(?:[1-9]|[1-9][0-9])$") private String bauleitung;

    @Column(nullable = false)
    @NotNull @Size(min = 1, max = 200) private String bezeichnung;

}
