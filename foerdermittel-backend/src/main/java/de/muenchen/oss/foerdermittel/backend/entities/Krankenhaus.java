package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "fp_krankenhaeuser")
public class Krankenhaus {
    @Id
    @Size(max = 1)
    @Column(name = "krhname", nullable = false, length = 1)
    private String krhname;

    @Size(max = 200)
    @NotNull
    @Column(name = "bezeichnung", nullable = false, length = 200)
    private String bezeichnung;

    @OneToMany(mappedBy = "krnKrhname")
    private Set<Projekt> projekte = new LinkedHashSet<>();


}