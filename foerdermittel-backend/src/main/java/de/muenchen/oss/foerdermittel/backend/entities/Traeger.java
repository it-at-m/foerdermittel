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
@Table(name = "fp_traeger")
public class Traeger {
    @Id
    @Column(name = "kurzform", nullable = false, precision = 1)
    private BigDecimal id;

    @Size(max = 200)
    @NotNull
    @Column(name = "bezeichnung", nullable = false, length = 200)
    private String bezeichnung;

    @OneToMany(mappedBy = "traKurzform")
    private Set<Staedtebaufoerderung> staedtebaufoerderungen = new LinkedHashSet<>();


}