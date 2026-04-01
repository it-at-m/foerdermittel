package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "fp_unterabschnitte")
public class Unterabschnitt {
    @Id
    @Size(max = 2)
    @Column(name = "ua", nullable = false, length = 2)
    private String ua;

    @Size(max = 200)
    @NotNull
    @Column(name = "bezeichnung", nullable = false, length = 200)
    private String bezeichnung;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "has_ha", nullable = false)
    private Hauptabschnitt hasHa;

    @OneToMany(mappedBy = "uasUa")
    private Set<Projekt> projekte = new LinkedHashSet<>();


}