package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "fp_stadtbezirke")
public class Stadtbezirk {
    @Id
    @Column(name = "stadtbezirk", nullable = false, precision = 2)
    private BigDecimal id;

    @Size(max = 200)
    @NotNull
    @Column(name = "bezeichnung", nullable = false, length = 200)
    private String bezeichnung;

    @OneToMany(mappedBy = "bezStadtbezirk")
    private Set<GeplanteMassnahme> geplanteMassnahmen = new LinkedHashSet<>();

    @OneToMany(mappedBy = "bezStadtbezirk")
    private Set<Projekt> projekte = new LinkedHashSet<>();

    @OneToMany(mappedBy = "bezStadtbezirk")
    private Set<Stadtbezirksliste> stadtbezirkslisten = new LinkedHashSet<>();


}