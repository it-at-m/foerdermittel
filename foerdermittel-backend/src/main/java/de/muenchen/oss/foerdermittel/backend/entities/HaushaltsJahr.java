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
@Table(name = "fp_hhjahre")
public class HaushaltsJahr {
    @Id
    @Column(name = "hhjahr", nullable = false, precision = 4)
    private BigDecimal id;

    @Size(max = 200)
    @NotNull
    @Column(name = "hhbezeichnung", nullable = false, length = 200)
    private String hhbezeichnung;

    @Column(name = "notizen", length = Integer.MAX_VALUE)
    private String notizen;

    @OneToMany(mappedBy = "hhjJahr")
    private Set<HaushaltsPlan> haushaltsPlaene = new LinkedHashSet<>();


}