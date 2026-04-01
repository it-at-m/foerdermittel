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
@Table(name = "fp_kurzbezeichnungen")
public class Kurzbezeichnung {
    @Id
    @Size(max = 8)
    @Column(name = "kurzbez", nullable = false, length = 8)
    private String kurzbez;

    @Size(max = 200)
    @NotNull
    @Column(name = "bezeichnung", nullable = false, length = 200)
    private String bezeichnung;

    @OneToMany(mappedBy = "kurKurzbez")
    private Set<GeplanteMassnahme> geplanteMassnahmen = new LinkedHashSet<>();

    @OneToMany(mappedBy = "kurKurzbez")
    private Set<Projekt> projekte = new LinkedHashSet<>();


}