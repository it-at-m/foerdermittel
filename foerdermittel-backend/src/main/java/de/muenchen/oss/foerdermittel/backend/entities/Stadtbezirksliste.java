package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "fp_stadtbezirkslisten")
public class Stadtbezirksliste {
    @EmbeddedId
    private StadtbezirkslisteId id;

    @MapsId("lnaKurzbez")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lna_kurzbez", nullable = false)
    private Listenname lnaKurzbez;

    @MapsId("bezStadtbezirk")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bez_stadtbezirk", nullable = false)
    private Stadtbezirk bezStadtbezirk;

    @Size(max = 200)
    @Column(name = "bezeichnung", length = 200)
    private String bezeichnung;


}