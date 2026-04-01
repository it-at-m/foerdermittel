package de.muenchen.oss.foerdermittel.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "fp_siedlungsgebiete")
public class Siedlungsgebiet {
    @Id
    @Column(name = "siedlungsgebiet", nullable = false, precision = 2)
    private BigDecimal id;

    @Size(max = 200)
    @NotNull
    @Column(name = "bezeichnung", nullable = false, length = 200)
    private String bezeichnung;

    @OneToMany(mappedBy = "sgtSiedlungsgebiet")
    private Set<Projekt> projekte = new LinkedHashSet<>();


}