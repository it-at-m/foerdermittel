package de.muenchen.oss.foerdermittel.backend.stichwortbereich;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class represents a Stichwortbereich.
 * <p>
 * The entity's attributes are mapped to the corresponding database columns.
 * </p>
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stichwortbereiche")
public class Stichwortbereich implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // ========= //
    // Variables //
    // ========= //

    @Column(nullable = false)
    @Id
    @NotNull @Size(min = 1, max = 30) @Pattern(regexp = "^[A-Z0-9\\-]+$") private String bereich;

    @Column(nullable = false)
    @NotNull @Size(min = 1, max = 200) private String bezeichnung;
}
