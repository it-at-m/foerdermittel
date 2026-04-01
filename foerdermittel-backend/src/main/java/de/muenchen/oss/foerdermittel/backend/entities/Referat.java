package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "fp_referate")
public class Referat {
    @Id
    @Column(name = "refnr", nullable = false, precision = 2)
    private BigDecimal id;

    @Size(max = 200)
    @Column(name = "bezeichnung", length = 200)
    private String bezeichnung;

    @OneToMany(mappedBy = "refRefnr")
    private Set<Abruf> abrufe = new LinkedHashSet<>();


}