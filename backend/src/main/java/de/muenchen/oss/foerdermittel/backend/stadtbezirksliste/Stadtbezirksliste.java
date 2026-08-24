package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stadtbezirkslisten")
public class Stadtbezirksliste implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private StadtbezirkslistePrimaryKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("listenname")
    @JoinColumn(name = "lna_kurzbez")
    private Listenname listenName;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("stadtbezirk")
    @JoinColumn(name = "bez_stadtbezirk")
    private Stadtbezirk stadtbezirk;

    @Column(name = "bezeichnung")
    private String bezeichnung;
}
