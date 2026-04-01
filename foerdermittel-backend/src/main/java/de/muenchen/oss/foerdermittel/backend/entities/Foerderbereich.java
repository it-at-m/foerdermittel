package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "fp_foerderbereiche")
public class Foerderbereich {
    @Id
    @Column(name = "fb", nullable = false, precision = 2)
    private BigDecimal id;

    @Size(max = 200)
    @NotNull
    @Column(name = "bezeichnung", nullable = false, length = 200)
    private String bezeichnung;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "funktion1", nullable = false)
    private Boolean funktion1;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "funktion2", nullable = false)
    private Boolean funktion2;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "funktion3", nullable = false)
    private Boolean funktion3;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "funktion4", nullable = false)
    private Boolean funktion4;

    @OneToMany(mappedBy = "fobFb")
    private Set<Projekt> projekte = new LinkedHashSet<>();


}