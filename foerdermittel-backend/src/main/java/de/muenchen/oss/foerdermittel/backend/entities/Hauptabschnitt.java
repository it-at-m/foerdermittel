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
@Table(name = "fp_hauptabschnitte")
public class Hauptabschnitt {
    @Id
    @Size(max = 2)
    @Column(name = "ha", nullable = false, length = 2)
    private String ha;

    @Size(max = 200)
    @NotNull
    @Column(name = "bezeichnung", nullable = false, length = 200)
    private String bezeichnung;

    @OneToMany(mappedBy = "hasHa")
    private Set<Unterabschnitt> unterabschnitte = new LinkedHashSet<>();


}