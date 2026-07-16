package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import de.muenchen.oss.foerdermittel.backend.listenname.Listenname;
import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * This class represents a Stadtbezirksliste.
 * <p>
 * The entity's attributes are mapped to the corresponding database columns.
 * </p>
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stadtbezirkslisten")
public class Stadtbezirksliste implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // ========= //
    // Variables //
    // ========= //

    @EmbeddedId
   private StadtbezirkslistePrimaryKey id;

    @MapsId("bezStadtbezirk")
    @ManyToOne
    @JoinColumn(name = "bez_stadtbezirk", referencedColumnName = "stadtbezirk", insertable = false, updatable = false)
    private Stadtbezirk bezirk;

    @MapsId("lnaKurzbez")
    @ManyToOne
    @JoinColumn(name = "lna_kurzbez", referencedColumnName = "kurzbez", insertable = false, updatable = false)
    private Listenname listenName;

    @Column(nullable = false)
    @NotNull @Size(min = 1, max = 200) private String bezeichnung;

}
