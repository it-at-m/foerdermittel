package de.muenchen.oss.foerdermittel.backend.listenname;

import de.muenchen.oss.foerdermittel.backend.stadtbezirksliste.Stadtbezirksliste;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Listenname.
 * <p>
 * The entity's attributes are mapped to the corresponding database columns.
 * </p>
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "listennamen")
public class Listenname implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // ========= //
    // Variables //
    // ========= //

    @Column(nullable = false)
    @Id
    @NotNull @Size(min = 1, max = 3) @Pattern(regexp = "^[A-Z0-9]{1,3}$") private String kurzbez;

    @Column(nullable = false)
    @NotNull @Size(min = 1, max = 200) private String bezeichnung;

    @OneToMany(
            mappedBy = "listenName"
    )
    private List<Stadtbezirksliste> stadtbezirke = new ArrayList<>();

}
