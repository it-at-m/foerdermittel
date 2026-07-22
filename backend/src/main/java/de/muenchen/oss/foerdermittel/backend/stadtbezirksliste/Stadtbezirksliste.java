package de.muenchen.oss.foerdermittel.backend.stadtbezirksliste;

import de.muenchen.oss.foerdermittel.backend.stadtbezirk.Stadtbezirk;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;


@Entity
@Data
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


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("stadtbezirk")
    @JoinColumn(name = "bez_stadtbezirk")
    private Stadtbezirk stadtbezirk;


    @Column(name = "bezeichnung")
    private String bezeichnung;

}