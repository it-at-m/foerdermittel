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
@Table(name = "fp_bauleitungen")
public class Bauleitung {
    @Id
    @Size(max = 1)
    @Column(name = "bauleitung", nullable = false, length = 1)
    private String bauleitung;

    @Size(max = 200)
    @NotNull
    @Column(name = "bezeichnung", nullable = false, length = 200)
    private String bezeichnung;

    @OneToMany(mappedBy = "bleBauleitung")
    private Set<Projekt> projekte = new LinkedHashSet<>();


}